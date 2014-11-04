package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.PAMath;

public class CAWorker
{
	public static class Overload
	{
		private final ListMetaType _type;
		private final int _ndx;
		private float _ovr;
		public Overload(ListMetaType type, int ndx, float ovrld)
		{
			_type = type;
			_ndx = ndx;
			_ovr = ovrld;
		}
		public ListMetaType getType() {return _type;}
		public int getIndex() {return _ndx;}
		public float getOverPct() {return _ovr;}
		
		@Override
		public boolean equals(Object obj)
		{
			Overload o = (Overload) obj;
			return _type == o._type && _ndx == o._ndx;
		}
		@Override
		public int hashCode()
		{
			return 31 * _type.ordinal() + 7*_ndx;
		}
		
	}
	
	public static class Results
	{
		Set<Status> _status;
		Map<ListMetaType, Set<Overload>> _overloads;
		Set<VoltViol> _vv;
		float _ldrop;
		
		Results(Set<Status> s, Map<ListMetaType, Set<Overload>> ovl, float ldrop, Set<VoltViol> vv)
		{
			_status = s;
			_overloads = ovl;
			_ldrop = ldrop;
			_vv = vv;
		}
		
		public Set<Status> getStatus() {return _status;}
		public Map<ListMetaType, Set<Overload>> getOverloads() {return _overloads;}
		/** get the pct system load dropped / 100 */
		public float getLoadDropped() {return _ldrop;}
		public Set<VoltViol> getVoltViol() {return _vv;}
		@Override
		public String toString()
		{
			return _status.toString();
		}
		
	}
	public enum Status
	{
		Success(6), LoadLoss(1), IslandSplit(3), Overloads(2), VoltageCollapse(0), HighVoltage(4), OvrldMute(5);
		static final Status[] StatusByCode = new Status[]{
			VoltageCollapse, LoadLoss, Overloads, IslandSplit, HighVoltage, OvrldMute, Success
		};
		Status(int dbcode) {_dbcode = dbcode;}
		int _dbcode;
		public int getCode() {return _dbcode;}
		public static Status fromCode(int code) {return StatusByCode[code];}
	}
	
	PAModel _m;
	IslandConv[] _pfres;
	boolean _dbg = false;
	private String _cname;
	BusList _buses;
	
	public CAWorker(PAModel model, String cname)
	{
		_m = model;
		_cname = cname;
	}
	
	public void setDbg(boolean d) {_dbg = d;}
	
	public void runContingency() throws PAModelException
	{
		FDPFCore pf = null;
		PFMismatchDbg d = null;
		if (_dbg)
		{
			//TODO:  make this configurable
			d = new PFMismatchDbg(new File(new File("/run/shm/pfdbg"), _cname));
			pf = d.getPF(_m);
		}
		else
			pf = new FDPFCore(_m);

		pf.setMaxIterations(100);
		_pfres = pf.runPF();
		pf.updateResults();
		_buses = pf.getBuses();
		if (_dbg) try
		{
			d.write();
		}
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}
	public static class VoltViol
	{
		Bus _bus;
		float _v;
		VoltViol(Bus bus, float v)
		{
			_bus = bus;
			_v = v;
		}
		public Bus getBus() {return _bus;}
		public float getV() {return _v;}
	}
	
	public IslandConv[] getPFResults() {return _pfres;}
	
	public Results getResults(IslandConv[] orig) throws PAModelException
	{
		EnumSet<Status> rv = EnumSet.noneOf(Status.class);
		float old=0f, nld=0f;
		Set<VoltViol> vv = new HashSet<>(_pfres.length);
		for(IslandConv i : orig)
			old += i.getLoadMW();
		Set<Island> collapsed = new HashSet<>(_pfres.length);
		
		for(IslandConv i : _pfres)
		{
			nld += i.getLoadMW();
			if (i.lvFail())
			{
				vv.add(new VoltViol(_buses.get(i.lvBus()), i.lowestV()));
				rv.add(Status.VoltageCollapse);
				collapsed.add(i.getIsland());
			}
			else if (i.hvFail())
			{
				vv.add(new VoltViol(_buses.get(i.hvBus()), i.highestV()));
				rv.add(Status.HighVoltage);
			}
		}
		
		float pct = nld/old;
		if(pct < .99f) //TODO: make this tunable
			rv.add(Status.LoadLoss);
		else if (orig.length != _pfres.length)
			rv.add(Status.IslandSplit);
		
		
		
		Map<ListMetaType,Set<Overload>> ovl = getOverloads(collapsed);
		int novl = 0; for(Set<Overload> l : ovl.values()) novl += l.size();
		if (novl > 0)
			rv.add(Status.Overloads);

		if (rv.isEmpty()) rv.add(Status.Success);
		
		return new Results(rv, ovl, 1f-pct, vv);
	}
	
	public Map<ListMetaType,Set<Overload>> getOverloads(Set<Island> collapsed) throws PAModelException
	{
		Map<ListMetaType,Set<Overload>> rv = new EnumMap<>(ListMetaType.class);
		for(ACBranchList list : _m.getACBranches())
		{
			Set<Overload> r = new HashSet<>();
			for(ACBranch d : list)
			{
				ListMetaType t = list.getListMeta();
				if (!d.isOutOfSvc()
						&& !collapsed.contains(d.getFromBus().getIsland())
						&& !collapsed.contains(d.getToBus().getIsland()))
				{
					float mva = Math.max(
						PAMath.calcMVA(d.getFromP(), d.getFromQ()),
						PAMath.calcMVA(d.getToP(), d.getToQ())),
						mrat = d.getLTRating();
					if (mva > mrat) r.add(new Overload(t, d.getIndex(), mva/mrat));
				}
				rv.put(t, r);
			}
		}
		
		return rv;
	}
	
	
}
