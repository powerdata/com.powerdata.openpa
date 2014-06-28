package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.tools.GroupMap;

public class GroupList<T extends Group> extends BaseList<T>
{
	protected abstract class EquipListList<U extends BaseList<? extends BaseObject>> extends AbstractList<U>
	{
		@Override
		public int size() {return _size;}
	}
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

	protected GroupList() {super();}
	
	public GroupList(PALists model, int[] keys, BusGrpMap busgrp)
	{
		super(model, keys);
		_bgmap = busgrp;
		Arrays.fill(_lstref, new WeakReference<>(null));
	}

	public GroupList(PALists model, BusGrpMap busgrp)
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
	
//	protected List<int[]> getAreaMap()
//	{
//		List<int[]> map = _areamap.get();
//		if (map == null)
//		{
//			map = mapAreas();
//			_areamap = new WeakReference<>(map);
//		}
//		return map;
//	}
	
//	protected List<int[]> mapAreas()
//	{
//		List<int[]> bgrp = _bgmap.map();
//		BusList buses = _model.getBuses();
//		int cnt = buses.size();
//		int[] map = new int[cnt];
//		Arrays.fill(map, -1);
//		for(int[] g : bgrp)
//		{
//			for (int b : g)
//				map[g] = buses.getArea(b).getIndex();
//		}
//		return new GroupMap(map, _bgmap.size())
//	}
	/**
	 * return list of areas
	 */
	public AreaList getAreas(int ndx)
	{
//		AreaList list = _model.getAreas();
//		return new AreaSubList(_model, )
		//TODO:
		return AreaList.Empty;
	}
	
	/**
	 * return list of buses as appropriate.
	 */
	public BusList getBuses(int ndx)
	{
		BusList list = _model.getBuses();
		return new BusSubList(_model, list, _bgmap.map().get(ndx));
	}
	public List<BusList> getBuses()
	{
		return new EquipListList<BusList>()
		{
			@Override
			public BusList get(int index)
			{
				return getBuses(index);
			}
		};
	}
	/** return list of switches */
	public SwitchList getSwitches(int ndx)
	{
		SwitchList list = _model.getSwitches();
		return new SwitchSubList(_model, list, getMap2T(EqType.SW, list).get(ndx));
	}
	public List<SwitchList> getSwitches()
	{
		return new EquipListList<SwitchList>()
		{
			@Override
			public SwitchList get(int index)
			{
				return getSwitches(index);
			}
		};
	}

	/** return list of lines */
	public LineList getLines(int ndx)
	{
		LineList list = _model.getLines();
		return new LineSubList(_model, list,
			getMap2T(EqType.LN, list).get(ndx));
	}
	public List<LineList> getLines()
	{
		return new EquipListList<LineList>()
		{
			@Override
			public LineList get(int index)
			{
				return getLines(index);
			}
		};
	}

	/** return list of series reactors */
	public SeriesReacList getSeriesReactors(int ndx)
	{
		SeriesReacList list = _model.getSeriesReactors();
		return new SeriesReacSubList(_model, list,
			getMap2T(EqType.SR, list).get(ndx));
	}
	public List<SeriesReacList> getSeriesReactors()
	{
		return new EquipListList<SeriesReacList>()
		{
			@Override
			public SeriesReacList get(int index)
			{
				return getSeriesReactors(index);
			}
		};
	}
	
	/** return list of series capacitors */
	public SeriesCapList getSeriesCapacitors(int ndx)
	{
		SeriesCapList list = _model.getSeriesCapacitors();
		return new SeriesCapSubList(_model, list,
			getMap2T(EqType.SC, list).get(ndx));
	}
	public List<SeriesCapList> getSeriesCapacitors()
	{
		return new EquipListList<SeriesCapList>()
		{
			@Override
			public SeriesCapList get(int index)
			{
				return getSeriesCapacitors(index);
			}
		};
	}
	
	/** return list of transformers */
	public TransformerList getTransformers(int ndx)
	{
		TransformerList list = _model.getTransformers();
		return new TransformerSubList(_model, list,
			getMap2T(EqType.TX, list).get(ndx));
	}
	public List<TransformerList> getTransformers()
	{
		return new EquipListList<TransformerList>()
		{
			@Override
			public TransformerList get(int index)
			{
				return getTransformers(index);
			}
		};
	}
	
	/** return list of phase shifters */
	public PhaseShifterList getPhaseShifters(int ndx)
	{
		PhaseShifterList list = _model.getPhaseShifters();
		return new PhaseShifterSubList(_model, list,
			getMap2T(EqType.PS, list).get(ndx));
	}
	public List<PhaseShifterList> getPhaseShifters()
	{
		return new EquipListList<PhaseShifterList>()
		{
			@Override
			public PhaseShifterList get(int index)
			{
				return getPhaseShifters(index);
			}
		};
	}
	
	/** return list of two-terminal DC Lines */
	public TwoTermDCLineList getTwoTermDCLines(int ndx)
	{
		TwoTermDCLineList list = _model.getTwoTermDCLines();
		return new TwoTermDCLineSubList(_model, list,
			getMap2T(EqType.D2, list).get(ndx));
	}
	public List<TwoTermDCLineList> getTwoTermDCLines()
	{
		return new EquipListList<TwoTermDCLineList>()
		{
			@Override
			public TwoTermDCLineList get(int index)
			{
				return getTwoTermDCLines(index);
			}
		};
	}
	
	/** return list of generators */
	public GenList getGenerators(int ndx)
	{
		GenList list = _model.getGenerators();
		return new GenSubList(_model, list,
			getMap1T(EqType.GEN, list).get(ndx));
	}
	public List<GenList> getGenerators()
	{
		return new EquipListList<GenList>()
		{
			@Override
			public GenList get(int index)
			{
				return getGenerators(index);
			}
		};
	}
	
	/** return list of loads */
	public LoadList getLoads(int ndx)
	{
		LoadList list = _model.getLoads();
		return new LoadSubList(_model, list,
			getMap1T(EqType.LD, list).get(ndx));
	}
	public List<LoadList> getLoads()
	{
		return new EquipListList<LoadList>()
		{
			@Override
			public LoadList get(int index)
			{
				return getLoads(index);
			}
		};
	}
	
	/** return list of shunt reactors */
	public ShuntReacList getShuntReactors(int ndx)
	{
		ShuntReacList list = _model.getShuntReactors();
		return new ShuntReacSubList(_model, list,
			getMap1T(EqType.SHR, list).get(ndx));
	}
	public List<ShuntReacList> getShuntReactors()
	{
		return new EquipListList<ShuntReacList>()
		{
			@Override
			public ShuntReacList get(int index)
			{
				return getShuntReactors(index);
			}
		};
	}
	
	/** return list of shunt capacitors */
	public ShuntCapList getShuntCapacitors(int ndx)
	{
		ShuntCapList list = _model.getShuntCapacitors();
		return new ShuntCapSubList(_model, list,
			getMap1T(EqType.SHC, list).get(ndx));
	}
	public List<ShuntCapList> getShuntCapacitors()
	{
		return new EquipListList<ShuntCapList>()
		{
			@Override
			public ShuntCapList get(int index)
			{
				return getShuntCapacitors(index);
			}
		};
	}

	/** return list of switched shunts */
	public SwitchedShuntList getSwitchedShunts(int ndx)
	{
		SwitchedShuntList list = _model.getSwitchedShunts();
		return new SwitchedShuntSubList(_model, list,
			getMap1T(EqType.SWSH, list).get(ndx));
	}
	public List<SwitchedShuntList> getSwitchedShunts()
	{
		return new EquipListList<SwitchedShuntList>()
		{
			@Override
			public SwitchedShuntList get(int index)
			{
				return getSwitchedShunts(index);
			}
		};
	}
	
	/** return list of SVC's */
	public SVCList getSVCs(int ndx)
	{
		SVCList list = _model.getSVCs();
		return new SVCSubList(_model, list,
			getMap1T(EqType.SVC, list).get(ndx));
	}
	public List<SVCList> getSVCs()
	{
		return new EquipListList<SVCList>()
		{
			@Override
			public SVCList get(int index)
			{
				return getSVCs(index);
			}
		};
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new Group(this, index);
	}

	public T getByBus(Bus b)
	{
		return get(_bgmap.getGrp(b.getIndex()));
	}
}
