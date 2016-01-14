package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.impl.AbstractPAList;
import com.powerdata.openpa.impl.BusSubList;
import com.powerdata.openpa.impl.GenSubList;
import com.powerdata.openpa.impl.GroupMap;
import com.powerdata.openpa.impl.LineSubList;
import com.powerdata.openpa.impl.LoadSubList;
import com.powerdata.openpa.impl.PAModelI;
import com.powerdata.openpa.impl.PhaseShifterSubList;
import com.powerdata.openpa.impl.SVCSubList;
import com.powerdata.openpa.impl.SeriesCapSubList;
import com.powerdata.openpa.impl.SeriesReacSubList;
import com.powerdata.openpa.impl.ShuntCapSubList;
import com.powerdata.openpa.impl.ShuntReacSubList;
import com.powerdata.openpa.impl.SwitchSubList;
import com.powerdata.openpa.impl.TransformerSubList;
import com.powerdata.openpa.impl.TwoTermDCLineSubList;

public abstract class GroupListI<T extends Group> extends AbstractPAList<T> implements GroupListIfc<T>
{
	enum EqType
	{
		/** Generator */
		GEN,
		/** Load */
		LD,
		/** Shunt capacitor */
		SHC,
		/** Shunt Reactor */
		SHR,
		/** Switched Shunt */
		SWSH,
		/** Static Var Compensator */
		SVC,
		/** Switch */
		SW,
		/** Line */
		LN,
		/** Series Reactor */
		SR,
		/** Series Capacitor */
		SC,
		/** Transformer */
		TX,
		/** Phase Shifter */
		PS,
		/** Two-Terminal DC Line */
		D2
	}
	
	protected GroupIndex _bgmap;
	WeakReference<List<int[]>> _areamap = new WeakReference<>(null);

	protected GroupListI() {super();}
	
	PALists _lmodel;
	
	protected GroupListI(PALists model, int[] keys, GroupIndex busgrp)
	{
		super(null, keys, null);
		_lmodel = model;
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	protected GroupListI(PALists model, GroupIndex busgrp)
	{
		super(null, busgrp.size(), null);
		_lmodel = model;
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	protected GroupListI(PAModelI model, int[] keys, GroupIndex busgrp, PAListEnum pfld)
	{
		super(model, keys, pfld);
		_lmodel = model;
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	protected GroupListI(PAModelI model, GroupIndex busgrp, PAListEnum pfld)
	{
		super(model, busgrp.size(), pfld);
		_lmodel = model;
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}
	
	protected GroupListI(PAModelI model, int size, PAListEnum pfld)
	{
		super(model, size, pfld);
		_lmodel = model;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	@SuppressWarnings("unchecked")
	WeakReference<List<int[]>>[] _lstref =
		new WeakReference[EqType.values().length];
	
	/**
	 * resolve a possibly indexed bus or group identifier.
	 */
	protected int resolveIndex(Bus b)
	{
		return _bgmap.getGrp(b.getIndex());
	}

	protected void setupMap(GroupIndex idx)
	{
		_bgmap = idx;
		_size = _bgmap.size();
	}

	/** get equipment lists for each bus */
	protected List<int[]> getMap2T(EqType ltype,
			TwoTermDevListIfc<? extends TwoTermDev> list) throws PAModelException
	{
		int lx = ltype.ordinal();
		List<int[]> map = _lstref[lx].get();
		if (map == null)
		{
			map = map2TList(list);
			_lstref[lx] = new WeakReference<>(map);
		}
		return map;
	}

	/** recreate the mapping if it fell out of the cache */
	protected List<int[]> map2TList(TwoTermDevListIfc<? extends TwoTermDev> list) throws PAModelException
	{
		int lcnt = list.size();
		int[] fmap = new int[lcnt], tmap = new int[lcnt];
		for (int il = 0; il < lcnt; ++il)
		{
			TwoTermDev d = list.get(il);
			int f = resolveIndex(d.getFromBus());
			int t = resolveIndex(d.getToBus());
			fmap[il] = f;
			tmap[il] = (f == t) ? -1 : t;
		}
		return new GroupMap(fmap, tmap, _bgmap.size());
	}

	/** get equipment lists for each bus (1-terminal) */
	protected List<int[]> getMap1T(EqType ltype,
			OneTermDevListIfc<? extends OneTermDev> list) throws PAModelException
	{
		int lx = ltype.ordinal();
		List<int[]> map = _lstref[lx].get();
		if (map == null)
		{
			map = map1TList(list);
			_lstref[lx] = new WeakReference<>(map);
		}
		return map;
	}

	protected List<int[]> map1TList(OneTermDevListIfc<? extends OneTermDev> list) throws PAModelException
	{
		int cnt = list.size();
		int[] map = new int[cnt];
		for (int i = 0; i < cnt; ++i)
		{
			map[i] = resolveIndex(list.getBus(i));
		}
		return new GroupMap(map, _bgmap.size());
	}
	
	/**
	 * return list of buses as appropriate.
	 */
	@Override
	public BusList getBuses(int ndx) throws PAModelException
	{
		BusList list = _lmodel.getBuses();
		return new BusSubList(list, _bgmap.map().get(ndx));
	}
	
	/** return list of switches */
	@Override
	public SwitchList getSwitches(int ndx) throws PAModelException
	{
		SwitchList list = _lmodel.getSwitches();
		return new SwitchSubList(list, getMap2T(EqType.SW, list).get(ndx));
	}
	/** return list of lines */
	@Override
	public LineList getLines(int ndx) throws PAModelException
	{
		LineList list = _lmodel.getLines();
		return new LineSubList(list,
			getMap2T(EqType.LN, list).get(ndx));
	}
	/** return list of series reactors */
	@Override
	public SeriesReacList getSeriesReactors(int ndx) throws PAModelException
	{
		SeriesReacList list = _lmodel.getSeriesReactors();
		return new SeriesReacSubList(list,
			getMap2T(EqType.SR, list).get(ndx));
	}
	
	/** return list of series capacitors */
	@Override
	public SeriesCapList getSeriesCapacitors(int ndx) throws PAModelException
	{
		SeriesCapList list = _lmodel.getSeriesCapacitors();
		return new SeriesCapSubList(list,
			getMap2T(EqType.SC, list).get(ndx));
	}
	/** return list of transformers */
	@Override
	public TransformerList getTransformers(int ndx) throws PAModelException
	{
		TransformerList list = _lmodel.getTransformers();
		return new TransformerSubList(list,
			getMap2T(EqType.TX, list).get(ndx));
	}
	
	/** return list of phase shifters */
	@Override
	public PhaseShifterList getPhaseShifters(int ndx) throws PAModelException
	{
		PhaseShifterList list = _lmodel.getPhaseShifters();
		return new PhaseShifterSubList(list,
			getMap2T(EqType.PS, list).get(ndx));
	}
	
	/** return list of two-terminal DC Lines */
	@Override
	public TwoTermDCLineList getTwoTermDCLines(int ndx) throws PAModelException
	{
		TwoTermDCLineList list = _lmodel.getTwoTermDCLines();
		return new TwoTermDCLineSubList(list,
			getMap2T(EqType.D2, list).get(ndx));
	}
	
	/** return list of generators */
	@Override
	public GenList getGenerators(int ndx) throws PAModelException
	{
		GenList list = _lmodel.getGenerators();
		return new GenSubList(list,
			getMap1T(EqType.GEN, list).get(ndx));
	}

	/** return list of loads */
	@Override
	public LoadList getLoads(int ndx) throws PAModelException
	{
		LoadList list = _lmodel.getLoads();
		return new LoadSubList(list,
			getMap1T(EqType.LD, list).get(ndx));
	}
	/** return list of shunt reactors */
	@Override
	public ShuntReacList getShuntReactors(int ndx) throws PAModelException
	{
		ShuntReacList list = _lmodel.getShuntReactors();
		return new ShuntReacSubList(list,
			getMap1T(EqType.SHR, list).get(ndx));
	}
	
	/** return list of shunt capacitors */
	@Override
	public ShuntCapList getShuntCapacitors(int ndx) throws PAModelException
	{
		ShuntCapList list = _lmodel.getShuntCapacitors();
		return new ShuntCapSubList(list,
			getMap1T(EqType.SHC, list).get(ndx));
	}

	/** return list of SVC's */
	@Override
	public SVCList getSVCs(int ndx) throws PAModelException
	{
		SVCList list = _lmodel.getSVCs();
		return new SVCSubList(list,
			getMap1T(EqType.SVC, list).get(ndx));
	}

	@Override
	public T getByBus(Bus b)
	{
		return get(_bgmap.getGrp(b.getIndex()));
	}

	@Override
	public int[] translateBusIndexes(int[] indexes)
	{
		int n = indexes.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
		{
			rv[i] = _bgmap.getGrp(indexes[i]);
		}
		return rv;
	}
	
}
