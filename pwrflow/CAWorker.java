package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.GroupList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.Line;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.Switch.State;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.tools.PAMath;

public class CAWorker
{
	public static class Result
	{
		final Status _s;
		public Result(Status s)
		{
			_s = s;
		}
		@Override
		public int hashCode()
		{
			return _s.ordinal() * 67;
		}
		@Override
		public boolean equals(Object obj)
		{
			return _s == ((Result) obj)._s;
		}
		public BaseObject getDevice()
		{
			return null;
		}
		public Status getStatus()
		{
			return _s;
		}
		public Float getValue()
		{
			return null;
		}
		public String getUnits()
		{
			return "";
		}
		@Override
		public String toString()
		{
			BaseObject d = getDevice();
			return String.format("%s: \"%s\" %d", getStatus().toString(),
				(d == null) ? "<null>" : d.toString(), hashCode());
		}
	}

	static class ValuedResult extends Result
	{
		final float _violval;
		public ValuedResult(Status s, float violval)
		{
			super(s);
			_violval = violval;
		}
		@Override
		public Float getValue()
		{
			return _violval;
		}
	}

	static class Violation extends ValuedResult
	{
		final BaseObject _device;
		public Violation(Status s, BaseObject device, float violval)
		{
			super(s, violval);
			_device = device;
		}
		@Override
		public int hashCode()
		{
			return super.hashCode() + _device.hashCode();
		}
		@Override
		public boolean equals(Object obj)
		{
			return super.equals(obj)
					&& _device.equals(((Violation) obj)._device);
		}
		@Override
		public BaseObject getDevice()
		{
			return _device;
		}
	}

	class VoltageViol extends Violation
	{
		public VoltageViol(Status s, Bus b, float violval)
		{
			super(s, b, violval);
		}
		
	}

	public enum Status
	{
		Success(6), LoadLoss(1), IslandSplit(5), Overloads(2), VoltageCollapse(
				0), HighVoltageFail(3), LowVoltage(4), HighVoltage(7);
		static final Status[] StatusByCode = new Status[] { VoltageCollapse,
				LoadLoss, Overloads, HighVoltageFail, LowVoltage, IslandSplit,
				Success, HighVoltage };
		Status(int dbcode)
		{
			_dbcode = dbcode;
		}
		int _dbcode;
		public int getCode()
		{
			return _dbcode;
		}
		public static Status fromCode(int code)
		{
			return StatusByCode[code];
		}
	}
	PAModel _m;
	IslandConv[] _pfres;
	BusList _snglbus;
	boolean _dbg = false;
	private String _cname;
	static float _minv = 0.945f, _maxv = 1.054f;
	public CAWorker(PAModel model, String cname)
	{
		_m = model;
		_cname = cname;
	}
	public void setDbg(boolean d)
	{
		_dbg = d;
	}
	public void runContingency() throws PAModelException
	{
		FDPFCore pf = null;
		PFMismatchDbg d = null;
		File dir = null;
		if (_dbg)
		{
			// TODO: make this configurable
			dir = new File(String.format("/run/shm/contingency/%s", _cname));
			if (!dir.exists()) dir.mkdirs();
			d = new PFMismatchDbg(dir);
			pf = d.getPF(_m);
		}
		else
			pf = new FDPFCore(_m);
		pf.setMaxIterations(100);
		_pfres = pf.runPF();
		pf.updateResults();
		_snglbus = pf.getBuses();
		if (_dbg) try
		{
			d.write();
			new ListDumper().dump(_m, dir);
		}
		catch (IOException |ReflectiveOperationException e)
		{
			throw new PAModelException(e);
		}
	}
	public IslandConv[] getPFResults()
	{
		return _pfres;
	}
	public Set<Result> getResults(IslandConv[] orig) throws PAModelException
	{
		Set<Result> rv = new HashSet<Result>();
		/*
		 * Loop through our solved power flow results and: 1. Find voltage
		 * collapse bus 2. Find PF failure due to high voltage @ bus 4. sum up
		 * system load (nld)
		 */
		float old = 0f, nld = 0f;
		Set<Island> islandFailures = new HashSet<>();
		BusList mbuses = _m.getBuses();
		for (IslandConv i : _pfres)
		{
			nld += i.getLoadMW();
			if (i.lvFail())
			{
				Bus b = mbuses.getByBus(i.lvBus());
				rv.add(new VoltageViol(Status.VoltageCollapse, b, i
						.lowestV()));
			}
			else if (i.hvFail())
			{
				Bus b = mbuses.getByBus(i.hvBus());
				rv.add(new VoltageViol(Status.HighVoltageFail, b, i
						.highestV()));
			}
			if (!i.solved()) islandFailures.add(i.getIsland());
		}
		/*
		 * Detect system load drop
		 */
		for (IslandConv i : orig)
			old += i.getLoadMW();
		float pct = nld / old;
		if (pct < .99f) // TODO: make tunable
			rv.add(new ValuedResult(Status.LoadLoss, 1f - pct));
		else if (orig.length != _pfres.length)
			rv.add(new Result(Status.IslandSplit));
		rv.addAll(getOverloads());
		rv.addAll(getVoltageViolations(islandFailures));
		return rv;
	}

	class VoltagePockets extends GroupList
	{
		public VoltagePockets(PALists lists) throws PAModelException
		{
			super(lists, new BusGrpMapBldr(_m)
			{
				@Override
				protected boolean incSW(Switch d) throws PAModelException
				{
					return d.getState() == State.Closed;
				}
				private boolean test(TwoTermDev d) throws PAModelException
				{
					return !d.isOutOfSvc()
							&& vmInRange(d.getFromBus()) == vmInRange(d
									.getToBus());
				}
				private boolean vmInRange(Bus b) throws PAModelException
				{
					float vm = PAMath.vmpu(b);
					return (vm >= _minv && vm <= _maxv);
				}
				@Override
				protected boolean incLN(Line d) throws PAModelException
				{
					return test(d);
				}
				@Override
				protected boolean incSR(SeriesReac d) throws PAModelException
				{
					return test(d);
				}
				@Override
				protected boolean incSC(SeriesCap d) throws PAModelException
				{
					return test(d);
				}
				@Override
				protected boolean incTX(Transformer d) throws PAModelException
				{
					return test(d);
				}
				@Override
				protected boolean incPS(PhaseShifter d) throws PAModelException
				{
					return test(d);
				}
			}.addLines().addPhaseShifters().addSeriesCap().addSeriesReac()
					.addSwitches().addTransformers().getMap());
		}
	}

	@FunctionalInterface
	interface FloatPredicate
	{
		boolean test(float val);
	}

	@FunctionalInterface
	interface BiFloatPredicate
	{
		boolean test(float val, float comp);
		default BiFloatPredicate and(FloatPredicate p)
		{
			return (t, u) -> test(t, u) && p.test(t);
		}
	}
	static FloatPredicate Under = i -> i < _minv, Over = i -> i > _maxv;
	static BiFloatPredicate Min = (i, j) -> i < j, Max = (i, j) -> i > j;
	static BiFloatPredicate LMin = Min.and(Under), LMax = Max.and(Over);
	public Set<Result> getVoltageViolations(Set<Island> islandFailures) throws PAModelException
	{
		Set<Result> rv = new HashSet<>();
		VoltagePockets vplist = new VoltagePockets(_m);
		BusList cbuses = _m.getBuses();
		for (Group vp : vplist)
		{
			Island gi = vp.getBuses().get(0).getIsland();
			if (!islandFailures.contains(gi))
			{
				float viol = 1f;
				Bus vb = null;
				BusList buses = vp.getBuses();
				BiFloatPredicate fp = null;
				Status st = null;
				float svm = PAMath.vmpu(buses.get(0));
				if (buses.getIsland(0).isEnergized())
				{
					if (Under.test(svm))
					{
						fp = LMin;
						st = Status.LowVoltage;
					}
					else if (Over.test(svm))
					{
						fp = LMax;
						st = Status.HighVoltage;
					}
				}
				if (st != null)
				{
					for (Bus b : buses)
					{
						float vm = PAMath.vmpu(b);
						if (fp.test(vm, viol))
						{
							viol = vm;
							vb = b;
						}
					}
					rv.add(new VoltageViol(st, cbuses.getByBus(_snglbus.getByBus(vb)), viol));
				}
			}
		}
		return rv;
	}
	public Set<Result> getOverloads() throws PAModelException
	{
		Set<Result> rv = new HashSet<>();
		Set<Island> nsolved = new HashSet<>();
		for (IslandConv i : _pfres)
			if (!i.solved()) nsolved.add(i.getIsland());
		for (ACBranchList list : _m.getACBranches())
		{
			for (ACBranch d : list)
			{
				if (!d.isOutOfSvc()
						&& !nsolved.contains(d.getFromBus().getIsland())
						&& !nsolved.contains(d.getToBus().getIsland()))
				{
					float mva = Math.max(
						PAMath.calcMVA(d.getFromP(), d.getFromQ()),
						PAMath.calcMVA(d.getToP(), d.getToQ())), mrat = d
							.getLTRating();
					if (mva > mrat)
						rv.add(new Violation(Status.Overloads, d, mva / mrat));
				}
			}
		}
		return rv;
	}
}
