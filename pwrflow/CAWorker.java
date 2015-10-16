package com.powerdata.openpa.pwrflow;

import java.util.Set;
import java.util.HashSet;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.GroupList;
import com.powerdata.openpa.ElectricalIsland;
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
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;
import com.powerdata.openpa.pwrflow.ConvergenceList.WorstVoltage;
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
				0), HighVoltageFail(3), LowVoltage(4), HighVoltage(7), NoRating(8);
		static final Status[] StatusByCode = new Status[] { VoltageCollapse,
				LoadLoss, Overloads, HighVoltageFail, LowVoltage, IslandSplit,
				Success, HighVoltage, NoRating};
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
	ConvergenceList _pfres;
	BusList _snglbus;
	boolean _dbg = false;
	static float _minv = 0.948f, _maxv = 1.052f;
	public CAWorker(PAModel model, String cname)
	{
		_m = model;
	}
	public void runContingency() throws PAModelException
	{
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(_m);
		FDPowerFlow pf = new FDPowerFlow(_m, bri);
		pf.setMaxIterations(100);
		_pfres = pf.runPF();
		pf.updateResults();
		_snglbus = bri.getBuses();
	}
	public ConvergenceList getPFResults()
	{
		return _pfres;
	}
	
	public Set<Result> getResults(ConvergenceList orig) throws PAModelException
	{
		Set<Result> rv = new HashSet<Result>();
		/*
		 * Loop through our solved power flow results and: 1. Find voltage
		 * collapse bus 2. Find PF failure due to high voltage @ bus 4. sum up
		 * system load (nld)
		 */
		float old = 0f, nld = 0f;
		Set<ElectricalIsland> islandFailures = new HashSet<>();
		BusList mbuses = _m.getBuses();
		for (ConvergenceInfo i : _pfres)
		{
			nld += i.getLoadMW();
			ConvergenceList.Status cs = i.getStatus();
			if (cs == ConvergenceList.Status.VoltageCollapse)
			{
				WorstVoltage wv = i.getWorstVoltage();
				Bus b = mbuses.getByBus(wv.getBus());
				rv.add(new VoltageViol(Status.VoltageCollapse, b, wv.getVM()));
			}
			else if (cs == ConvergenceList.Status.HighVoltage)
			{
				WorstVoltage wv = i.getWorstVoltage();
				Bus b = mbuses.getByBus(wv.getBus());
				rv.add(new VoltageViol(Status.HighVoltageFail, b, wv.getVM()));
			}
			if (cs != ConvergenceList.Status.Converge) islandFailures.add(i.getIsland());
		}
		/*
		 * Detect system load drop
		 */
		for (ConvergenceInfo i : orig)
			old += i.getLoadMW();
		float pct = nld / old;
		if (pct < .99f) // TODO: make tunable
			rv.add(new ValuedResult(Status.LoadLoss, 1f - pct));
		else if (orig.size() != _pfres.size())
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
					return d.isInService()
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
	public Set<Result> getVoltageViolations(Set<ElectricalIsland> islandFailures) throws PAModelException
	{
		Set<Result> rv = new HashSet<>();
		VoltagePockets vplist = new VoltagePockets(_m);
		BusList cbuses = _m.getBuses();
		for (Group vp : vplist)
		{
			ElectricalIsland gi = vp.getBuses().get(0).getIsland();
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
		Set<ElectricalIsland> nsolved = new HashSet<>();
		for (ConvergenceInfo i : _pfres)
			if (i.getStatus() != ConvergenceList.Status.Converge) nsolved.add(i.getIsland());
		for (ACBranchList list : _m.getACBranches())
		{
			for (ACBranch d : list)
			{
				if (d.isInService()
						&& !nsolved.contains(d.getFromBus().getIsland())
						&& !nsolved.contains(d.getToBus().getIsland()))
				{
					float mva = Math.max(
						PAMath.calcMVA(d.getFromP(), d.getFromQ()),
						PAMath.calcMVA(d.getToP(), d.getToQ()));
					float mrat = d.getLTRating();
					if (mrat == 0f)
						rv.add(new Violation(Status.NoRating, d, mva));
					else if (mva > mrat)
					{
						float pct = mva / mrat;
						if (pct > 1.02f)
							rv.add(new Violation(Status.Overloads, d, pct));
					}
				}
			}
		}
		return rv;
	}
}
