package com.powerdata.openpa.psse.util;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PhaseShifterList;
import com.powerdata.openpa.psse.PsseLists;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.psse.SwitchedShuntList;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.psse.TwoTermDev;
import com.powerdata.openpa.tools.GroupMap;

public class BusGroupList extends BaseList<BusGroup>
{
	protected enum EqList
	{
		/** Bus */  			BUS,
		/** Generator */ 		GEN,
		/** Load */ 			LD,
		/** Line */ 			LN,
		/** Transformer */ 		TX,
		/** PhaseShifter */ 	PS,
		/** Switch */ 			SW,
		/** Shunt */ 			SH,
		/** SVC */ 				SVC,
		/** Switched Shunt */ 	SS,
		/** Two Term DC Line */	D2;
	}
	protected PsseLists	_model;
	/** number of groups */
	protected int _ngrp=0;
	protected int[] _bus2grp;
	
	@SuppressWarnings("unchecked")
	protected WeakReference<List<int[]>>[] _lstref = 
		new WeakReference[EqList.values().length];

	public BusGroupList(PsseLists plists) throws PsseModelException
	{
		_model = plists;
		Arrays.fill(_lstref, new WeakReference<List<int[]>>(null));
		_bus2grp = null;
	}
	
	/**
	 * Map objects on a grouping of buses
	 * @param plists PsseModel or other PsseLists object
	 * @param busmap Bus index to group index map
	 * @param ngrp number of groups to expect
	 */
	public BusGroupList(PsseLists plists, int[] busmap, int ngrp)
	{
		_model = plists;
		Arrays.fill(_lstref, new WeakReference<List<int[]>>(null));
		_bus2grp = busmap;
		_ngrp = ngrp;
	}
	

	/** find a group index for a bus 
	 * @throws PsseModelException */
	public int findGrpNdx(Bus bus) throws PsseModelException {return _bus2grp[bus.getRootIndex()];}
	/** find a group index for a bus (by bus index) 
	 * @throws PsseModelException */
	public int findGrpNdx(int busndx) throws PsseModelException {return _bus2grp[busndx];}
	/** find group for a bus 
	 * @throws PsseModelException */
	public BusGroup findGroup(Bus bus) throws PsseModelException {return get(findGrpNdx(bus));}
	/** find group for a bus by bus index 
	 * @throws PsseModelException */
	public BusGroup findGroup(int busndx) throws PsseModelException {return get(findGrpNdx(busndx));}
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getObjectID(0);
	}

	@Override
	public BusGroup get(int ndx)
	{
		return new BusGroup(this, ndx);
	}

	
	
	@Override
	public int size() {return _ngrp;}

	protected List<int[]> getListMap1T(EqList ltype,
			BaseList<? extends OneTermDev> list) throws PsseModelException
	{
		int idx = ltype.ordinal();
		List<int[]> gmap = _lstref[idx].get();
		if (gmap == null)
		{
			gmap = map1TList(ltype, list);
			_lstref[idx] = new WeakReference<>(gmap);
		}
		return gmap;
	}

	protected List<int[]> map1TList(EqList ltype, BaseList<? extends OneTermDev> list)
			throws PsseModelException
	{
		int cnt = list.size();
		int[] map = new int[cnt];
		for (int i = 0; i < cnt; ++i)
			map[i] = _bus2grp[list.get(i).getBus().getIndex()];

		System.err.format("%s cache miss\n", ltype.toString());
		return new GroupMap(map, _ngrp);
	}

	protected class GrpMapDev2T extends AbstractList<int[]>
	{
		GroupMap _frm, _to;
		
		public GrpMapDev2T(int[] fmap, int[] tmap)
		{
			_frm = new GroupMap(fmap, _ngrp);
			_to = new GroupMap(tmap, _ngrp);
		}

		@Override
		public int size() {return _ngrp;}

		@Override
		public int[] get(int grpndx)
		{
			int fcnt = _frm.getCount(grpndx);
			int cnt = fcnt + _to.getCount(grpndx);
			int[] rv = new int[cnt];
			_frm.fill(rv, 0, grpndx);
			_to.fill(rv, fcnt, grpndx);
			return rv;
		}
		
	}

	protected List<int[]> getListMap2T(EqList ltype,
			BaseList<? extends TwoTermDev> list) throws PsseModelException
	{
		int idx = ltype.ordinal();
		List<int[]> gmap = _lstref[idx].get();
		if (gmap == null)
		{
			gmap = map2TList(ltype, list);
			_lstref[idx] = new WeakReference<>(gmap);
		}
		return gmap;
	}

	protected List<int[]> map2TList(EqList ltype,
			BaseList<? extends TwoTermDev> list) throws PsseModelException
	{
		int cnt = list.size();
		int[] fmap = new int[cnt], tmap = new int[cnt];
		for (int i = 0; i < cnt; ++i)
		{
			TwoTermDev d = list.get(i);
			int f = _bus2grp[d.getFromBus().getIndex()];
			int t = _bus2grp[d.getToBus().getIndex()];
			fmap[i] = f;
			tmap[i] = (f == t) ? -1 : t;
		}
		System.err.format("%s cache miss\n", ltype.toString());
		return new GrpMapDev2T(fmap, tmap);
	}
	
	protected List<int[]> getBusGrpMap()
	{
		int idx = EqList.BUS.ordinal();
		List<int[]> gmap = _lstref[idx].get();
		if (gmap ==  null)
		{
			gmap = new GroupMap(_bus2grp, _ngrp);
			_lstref[idx] = new WeakReference<>(gmap);
			System.err.println("Bus Group Map cache miss");
		}
		return gmap;
	}

	public int[] getBusNdxs(int ndx)
	{
		return getBusGrpMap().get(ndx);
	}
	
	public BusList getBuses(int ndx) throws PsseModelException
	{
		return new BusSubList(_model.getBuses(), getBusGrpMap().get(ndx));
	}

	public GenList getGenerators(int ndx) throws PsseModelException
	{
		GenList g = _model.getGenerators();
		return new GenSubList(g, getListMap1T(EqList.GEN, g).get(ndx));
	}
	public LoadList getLoads(int ndx) throws PsseModelException
	{
		LoadList l = _model.getLoads();
		return new LoadSubList(l, getListMap1T(EqList.LD, l).get(ndx));
	}
	
	public LineList getLines(int ndx) throws PsseModelException
	{
		LineList l = _model.getLines();
		return new LineSubList(l, getListMap2T(EqList.LN, l).get(ndx));
	}
	public TransformerList getTransformers(int ndx) throws PsseModelException
	{
		TransformerList tlist = _model.getTransformers();
		return  new TransformerSubList(tlist, getListMap2T(EqList.TX, tlist).get(ndx));
	}
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException
	{
		PhaseShifterList pslist = _model.getPhaseShifters();
		return new PhaseShifterSubList(pslist, getListMap2T(EqList.PS, pslist).get(ndx));
	}
	public SwitchList getSwitches(int ndx) throws PsseModelException
	{
		SwitchList swlist = _model.getSwitches();
		return new SwitchSubList(swlist, getListMap2T(EqList.SW, swlist).get(ndx));
	}
	public ShuntList getShunts(int ndx) throws PsseModelException
	{
		ShuntList shlist = _model.getShunts();
		return new ShuntSubList(shlist, getListMap1T(EqList.SH, shlist).get(ndx));
	}
	public SvcList getSvcs(int ndx) throws PsseModelException
	{
		SvcList svclist = _model.getSvcs();
		return new SvcSubList(svclist, getListMap1T(EqList.SVC, svclist).get(ndx));
	}
	public SwitchedShuntList getSwitchedShunts(int ndx) throws PsseModelException
	{
		SwitchedShuntList shlist = _model.getSwitchedShunts();
		return new SwitchedShuntSubList(shlist, getListMap1T(EqList.SS, shlist).get(ndx));
	}
	public TwoTermDCLineList getTwoTermDCLines(int ndx) throws PsseModelException
	{
		TwoTermDCLineList dclist = _model.getTwoTermDCLines();
		return new TwoTermDCLineSubList(dclist, getListMap2T(EqList.D2, dclist).get(ndx));
	}

	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getObjectName(0);
	}

	@Override
	public String getFullName(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getFullName(0);
	}

	@Override
	public String getDebugName(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getDebugName(0);
	}

	@Deprecated /* does not belong on a generalized grouping */
	public Island getIsland(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getIsland(0);
	}

	@Deprecated /* does not belong on a generalized grouping */
	public BusTypeCode getBusType(int ndx) throws PsseModelException
	{
		BusTypeCode max = BusTypeCode.Unknown;
		for(Bus b : getBuses(ndx))
		{
			BusTypeCode t = b.getBusType();
			if (max.compareTo(t) < 0) max = t;
		}
		return max;
	}

	@Deprecated /* does not belong on a generalized grouping */
	public float getVArad(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getVArad(0);
	}
	
	@Deprecated /* does not belong on a generalized grouping */
	public float getVMpu(int ndx) throws PsseModelException
	{
		return getBuses(ndx).getVMpu(0);
	}
	
	public void dump(PrintWriter out) throws PsseModelException
	{
		for(BusGroup g : this)
		{
			out.format("-- %s -- \n\n", g.getDebugName());
			dumpList("Buses", g.getBuses());
		}
	}
	public void dumpList(String name, BaseList<? extends BaseObject> list)
	{
		
	}

	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public boolean isEnergized(int ndx) throws PsseModelException
	{
		return getIsland(ndx).isEnergized();
	}
	
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public BusGroupList getForType(BusTypeCode type) throws PsseModelException
	{
		int[] rv = new int[size()];
		int cnt = 0;
		for(BusGroup g : this)
		{
			if (g.getBusType() == type)
			{
				rv[cnt++] = g.getRootIndex();
			}
		}
		return new BusGroupSubList(this, Arrays.copyOf(rv, cnt));
	}
	
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public BusGroup2TDevList getForType(BusTypeCode type, Island island) throws PsseModelException
	{
		int[] rv = new int[size()];
		int cnt = 0;
		int ix = island.getRootIndex();
		for(BusGroup g : this)
		{
			if (g.getBusType() == type && g.getIsland().getRootIndex()==ix)
			{
				rv[cnt++] = g.getRootIndex();
			}
		}
		return new BusGroupSubList(this, Arrays.copyOf(rv, cnt));
		
	}

	@Override
	public long getKey(int ndx) {return ndx;}
}
