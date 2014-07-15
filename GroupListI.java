package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.PAModel.ListMetaType;
import com.powerdata.openpa.tools.GroupMap;

public class GroupListI<T extends Group> extends AbstractPAList<T> implements GroupList<T>
{
	public enum EqType
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
	
	protected BusGrpMap _bgmap;
	WeakReference<List<int[]>> _areamap = new WeakReference<>(null);

	protected GroupListI() {super();}
	
	public GroupListI(PAModel model, int[] keys, BusGrpMap busgrp)
	{
		super(model, keys);
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	public GroupListI(PAModel model, BusGrpMap busgrp)
	{
		super(model, busgrp.size());
		_bgmap = busgrp;
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


	/** get equipment lists for each bus */
	protected List<int[]> getMap2T(EqType ltype,
			TwoTermDevList<? extends TwoTermDev> list)
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
	protected List<int[]> map2TList(TwoTermDevList<? extends TwoTermDev> list)
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
			OneTermDevList<? extends OneTermDev> list)
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

	protected List<int[]> map1TList(OneTermDevList<? extends OneTermDev> list)
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
	public BusList getBuses(int ndx)
	{
		BusList list = _model.getBuses();
		return new BusSubList(list, _bgmap.map().get(ndx));
	}
	
	/** return list of switches */
	public SwitchList getSwitches(int ndx)
	{
		SwitchList list = _model.getSwitches();
		return new SwitchSubList(list, getMap2T(EqType.SW, list).get(ndx));
	}
	/** return list of lines */
	public LineList getLines(int ndx)
	{
		LineList list = _model.getLines();
		return new LineSubList(list,
			getMap2T(EqType.LN, list).get(ndx));
	}
	/** return list of series reactors */
	public SeriesReacList getSeriesReactors(int ndx)
	{
		SeriesReacList list = _model.getSeriesReactors();
		return new SeriesReacSubList(list,
			getMap2T(EqType.SR, list).get(ndx));
	}
	
	/** return list of series capacitors */
	public SeriesCapList getSeriesCapacitors(int ndx)
	{
		SeriesCapList list = _model.getSeriesCapacitors();
		return new SeriesCapSubList(list,
			getMap2T(EqType.SC, list).get(ndx));
	}
	/** return list of transformers */
	public TransformerList getTransformers(int ndx)
	{
		TransformerList list = _model.getTransformers();
		return new TransformerSubList(list,
			getMap2T(EqType.TX, list).get(ndx));
	}
	
	/** return list of phase shifters */
	public PhaseShifterList getPhaseShifters(int ndx)
	{
		PhaseShifterList list = _model.getPhaseShifters();
		return new PhaseShifterSubList(list,
			getMap2T(EqType.PS, list).get(ndx));
	}
	
	/** return list of two-terminal DC Lines */
	public TwoTermDCLineList getTwoTermDCLines(int ndx)
	{
		TwoTermDCLineList list = _model.getTwoTermDCLines();
		return new TwoTermDCLineSubList(list,
			getMap2T(EqType.D2, list).get(ndx));
	}
	
	/** return list of generators */
	public GenList getGenerators(int ndx)
	{
		GenList list = _model.getGenerators();
		return new GenSubList(list,
			getMap1T(EqType.GEN, list).get(ndx));
	}

	/** return list of loads */
	public LoadList getLoads(int ndx)
	{
		LoadList list = _model.getLoads();
		return new LoadSubList(list,
			getMap1T(EqType.LD, list).get(ndx));
	}
	/** return list of shunt reactors */
	public ShuntReacList getShuntReactors(int ndx)
	{
		ShuntReacList list = _model.getShuntReactors();
		return new ShuntReacSubList(list,
			getMap1T(EqType.SHR, list).get(ndx));
	}
	
	/** return list of shunt capacitors */
	public ShuntCapList getShuntCapacitors(int ndx)
	{
		ShuntCapList list = _model.getShuntCapacitors();
		return new ShuntCapSubList(list,
			getMap1T(EqType.SHC, list).get(ndx));
	}

	/** return list of switched shunts */
	public SwitchedShuntList getSwitchedShunts(int ndx)
	{
		SwitchedShuntList list = _model.getSwitchedShunts();
		return new SwitchedShuntSubList(list,
			getMap1T(EqType.SWSH, list).get(ndx));
	}
	
	/** return list of SVC's */
	public SVCList getSVCs(int ndx)
	{
		SVCList list = _model.getSVCs();
		return new SVCSubList(list,
			getMap1T(EqType.SVC, list).get(ndx));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new Group(this, index);
	}

	@Override
	public T getByBus(Bus b)
	{
		return get(_bgmap.getGrp(b.getIndex()));
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.AnonymousGroup;
	}

}
