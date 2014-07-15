package com.powerdata.openpa;

import java.io.File;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Open PA model.  Provides access to power network and equipment 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAModel implements PALists
{
	public enum ListMetaType
	{
		AnonymousGroup, Area, Owner, Station, VoltageLevel, Island, Bus, Switch, Line, 
		SeriesCap, SeriesReac, Transformer, PhaseShifter, TwoTermDCLine, 
		Gen, Load, ShuntReac, ShuntCap, SVC, SwitchedShunt
	}
	
	BusListI 			_buses;
	SwitchListImpl		_switches;
	LineListImpl		_lines;
	IslandList			_islands;
	AreaListImpl		_areas;
	OwnerListImpl		_owners;
	StationListImpl 	_stations;
	VoltageLevelListImpl _vlevs;
	TransformerListImpl	_transformers;
	PhaseShifterListImpl 	_phshifts;
	SeriesReacListImpl 		_serreacs;
	SeriesCapListImpl 		_sercaps;
	GenListI 			_gens;
	LoadListImpl 			_loads;
	ShuntReacListI 		_shuntreacs;
	ShuntCapListI 		_shuntcaps;
	TwoTermDCLineListImpl 	_t2dclines;
	SwitchedShuntListImpl 	_swshunts;
	SVCListImpl 			_svcs;
	 
	
	protected PAModel(){}
	
	Map<ListMetaType,AbstractPAList<? extends BaseObject>> _lists = new EnumMap<>(ListMetaType.class);
	
	public BaseList<? extends BaseObject> getList(ListMetaType type)
	{
		return _lists.get(type);
	}

	protected void loadComplete()
	{
		_lists.put(ListMetaType.Area, _areas);
		_lists.put(ListMetaType.Owner, _owners);
		_lists.put(ListMetaType.Station, _stations);
		_lists.put(ListMetaType.VoltageLevel, _vlevs);
		_lists.put(ListMetaType.Bus, _buses);
		_lists.put(ListMetaType.Switch, _switches);
		_lists.put(ListMetaType.Line, _lines);
		_lists.put(ListMetaType.SeriesCap, _sercaps);
		_lists.put(ListMetaType.SeriesReac, _serreacs);
		_lists.put(ListMetaType.Transformer, _transformers);
		_lists.put(ListMetaType.PhaseShifter, _phshifts);
		_lists.put(ListMetaType.TwoTermDCLine, _t2dclines);
		_lists.put(ListMetaType.Gen, _gens);
		_lists.put(ListMetaType.Load, _loads);
		_lists.put(ListMetaType.ShuntReac, _shuntreacs);
		_lists.put(ListMetaType.ShuntCap, _shuntcaps);
		_lists.put(ListMetaType.SVC, _svcs);
		_lists.put(ListMetaType.SwitchedShunt, _swshunts);
	}
	
	
	public IslandList getIslands()
	{
		return _islands;
	}
	
	@Nodump
	public IslandList refreshIslands()
	{
		_islands = new IslandListImpl(this);
		return _islands;
	}
	
	public AreaList getAreas()
	{
		return _areas;
	}
	
	public OwnerList getOwners()
	{
		return _owners;
	}
	
	public StationList getStations()
	{
		return _stations;
	}
	
	public VoltageLevelList getVoltageLevels()
	{
		return _vlevs;
	}

	@Override
	public BusList getBuses()
	{
		return _buses;
	}

	@Override
	public SwitchList getSwitches()
	{
		return _switches;
	}

	@Override
	public LineList getLines()
	{
		return _lines;
	}

	@Override
	public SeriesReacList getSeriesReactors()
	{
		return _serreacs;
	}

	@Override
	public SeriesCapList getSeriesCapacitors()
	{
		return _sercaps;
	}

	@Override
	public TransformerList getTransformers()
	{
		return _transformers;
	}

	@Override
	public PhaseShifterList getPhaseShifters()
	{
		return _phshifts;
	}

	@Override
	public GenList getGenerators()
	{
		return _gens;
	}

	@Override
	public LoadList getLoads()
	{
		return _loads;
	}

	@Override
	public ShuntReacList getShuntReactors()
	{
		return _shuntreacs;
	}

	@Override
	public ShuntCapList getShuntCapacitors()
	{
		return _shuntcaps;
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines()
	{
		return _t2dclines;
	}

	@Override
	public SwitchedShuntList getSwitchedShunts()
	{
		return _swshunts;
	}

	@Override
	public SVCList getSVCs()
	{
		return _svcs;
	}
	
//	public OneTermDevList<? extends OneTermDev> getOneTermDevs()
//	{
//		
//		return OneTermDevList.Empty;
//	}
//
//	public TwoTermDevList<? extends TwoTermDev> getTwoTermDevs()
//	{
//		
//		return TwoTermDevList.Empty;
//	}
	
	@FunctionalInterface
	public static interface IntFloatFunction
	{
		float apply(int ndx);
	}
	
	@FunctionalInterface
	public static interface IntIntFunction
	{
		int apply(int ndx);
	}
	
	@FunctionalInterface
	public static interface IntBoolFunction
	{
		boolean apply(int ndx);
	}
	
	
	public static abstract class ColChange
	{
		Enum<?> _cmeta;
		ListMetaType _lmeta;
		Supplier<int[]> _idx;
		
		ColChange(Enum<?> cmeta, ListMetaType lmeta, Supplier<int[]> index)
		{
			_cmeta = cmeta;
			_lmeta = lmeta;
			_idx = index;
		}
		
		public Enum<?> getColMeta() {return _cmeta;}
		public ListMetaType getListMeta() {return _lmeta;}
		public int[] getNdx() {return _idx.get();}
		public IntStream getNdxStream() {return Arrays.stream(_idx.get());}
		
//		public abstract IntFunction<String> stringAccess();
//		public abstract IntFloatFunction floatAccess();
//		public abstract IntIntFunction intAccess();
//		public abstract IntBoolFunction boolAccess();
//		public IntFunction<Enum<?>> enumAccess()
//		{
//			return i -> NoEnum.Unknown;
//		}
		public abstract String stringAccess(int i);
		public abstract float floatAccess(int i);
		public abstract int intAccess(int i);
		public abstract boolean boolAccess(int i);
		public Enum<?> enumAccess(int i)
		{
			return NoEnum.Unknown;
		}

		@Override
		public boolean equals(Object obj)
		{
			return _cmeta == ((ColChange)obj)._cmeta;
		}
	}
	
	public static class IntColChange extends ColChange
	{
		IntIntFunction _f;
		
		IntColChange(Enum<?> cmeta, ListMetaType lmeta, Supplier<int[]> index, IntIntFunction f)
		{
			super(cmeta, lmeta, index);
			_f = f;
		}

		@Override
		public String stringAccess(int i)
		{
			return String.valueOf(_f.apply(i));
		}

		@Override
		public float floatAccess(int i)
		{
			return (float) _f.apply(i);
		}

		@Override
		public int intAccess(int i)
		{
			return _f.apply(i);
		}

		@Override
		public boolean boolAccess(int i)
		{
			return _f.apply(i) != 0;
		}


//		@Override
//		public IntFunction<String> stringAccess()
//		{
//			return i -> String.valueOf(_f.apply(i));
//		}
//
//		@Override
//		public IntFloatFunction floatAccess()
//		{
//			return i -> (float) _f.apply(i);
//		}
//
//		@Override
//		public IntIntFunction intAccess()
//		{
//			return _f;
//		}
//
//		@Override
//		public IntBoolFunction boolAccess()
//		{
//			return i -> _f.apply(i) != 0f;
//		}
	}

	public static class FloatColChange extends ColChange
	{
		IntFloatFunction _f;
		
		FloatColChange(Enum<?> cmeta, ListMetaType lmeta, Supplier<int[]> index, IntFloatFunction f)
		{
			super(cmeta, lmeta, index);
			_f = f;
		}

		@Override
		public String stringAccess(int i)
		{
			return String.valueOf(_f.apply(i));
		}

		@Override
		public float floatAccess(int i)
		{
			return _f.apply(i);
		}

		@Override
		public int intAccess(int i)
		{
			return Math.round(_f.apply(i));
		}

		@Override
		public boolean boolAccess(int i)
		{
			return _f.apply(i) != 0f;
		}

//		@Override
//		public IntFunction<String> stringAccess()
//		{
//			return i -> String.valueOf(_f.apply(i));
//		}
//
//		@Override
//		public IntFloatFunction floatAccess()
//		{
//			return _f;
//		}
//
//		@Override
//		public IntIntFunction intAccess()
//		{
//			return i -> Math.round(_f.apply(i));
//		}
//
//		@Override
//		public IntBoolFunction boolAccess()
//		{
//			return i -> _f.apply(i) != 0;
//		}
	}

	public static class StringColChange extends ColChange
	{

		IntFunction<String> _f;

		StringColChange(Enum<?> cmeta, ListMetaType lmeta, Supplier<int[]> index, IntFunction<String> f)
		{
			super(cmeta, lmeta, index);
			_f = f;
		}

		@Override
		public String stringAccess(int i)
		{
			return _f.apply(i);
		}

		@Override
		public float floatAccess(int i)
		{
			return Float.parseFloat(_f.apply(i));
		}

		@Override
		public int intAccess(int i)
		{
			return Integer.parseInt(_f.apply(i));
		}

		@Override
		public boolean boolAccess(int i)
		{
			return Boolean.parseBoolean(_f.apply(i));
		}

//		@Override
//		public IntFunction<String> stringAccess()
//		{
//			return _f;
//		}
//
//		@Override
//		public IntFloatFunction floatAccess()
//		{
//			return i -> Float.parseFloat(_f.apply(i));
//		}
//
//		@Override
//		public IntIntFunction intAccess()
//		{
//			return i -> Integer.parseInt(_f.apply(i));
//		}
//
//		@Override
//		public IntBoolFunction boolAccess()
//		{
//			return i -> Boolean.parseBoolean(_f.apply(i));
//		}
//		
	}
	
	public static class EnumColChange extends ColChange
	{

		IntFunction<Enum<?>> _f;

		EnumColChange(Enum<?> cmeta, ListMetaType lmeta, Supplier<int[]> index, IntFunction<Enum<?>> f)
		{
			super(cmeta, lmeta, index);
			_f = f;
		}

		@Override
		public String stringAccess(int i)
		{
			return _f.apply(i).toString();
		}

		@Override
		public float floatAccess(int i)
		{
			return (float) _f.apply(i).ordinal();
		}

		@Override
		public int intAccess(int i)
		{
			return _f.apply(i).ordinal();
		}

		@Override
		public boolean boolAccess(int i)
		{
			throw new UnsupportedOperationException("No boolean equivalent for enumerated values");
		}

		
		
//		@Override
//		public IntFunction<String> stringAccess()
//		{
//			return i -> _f.apply(i).toString();
//		}
//
//		@Override
//		public IntFloatFunction floatAccess()
//		{
//			return i -> (float) _f.apply(i).ordinal();
//		}
//
//		@Override
//		public IntIntFunction intAccess()
//		{
//			return i -> _f.apply(i).ordinal();
//		}
//
//		@Override
//		public IntBoolFunction boolAccess()
//		{
//			throw new UnsupportedOperationException("No boolean equivalent for enumerated values");
//		}
	}
	
	Set<ColChange> _changedCols = new HashSet<>();
	
	protected void setChange(ColChange c)
	{
		_changedCols.add(c);
	}
	
	/** call for an event */
	public Set<ColChange> getChanges()
	{
		return _changedCols;
	}
	
	public void clearChanges()
	{
		_lists.values().stream().forEach(o -> o.clearChanges());
	}

	public static interface HasMetaList
	{
		ListMetaType getListMeta();
	}
	
	/** placeholder for column change class conversions */
	public enum NoEnum {Unknown};
	
	
	public static void main(String... args) throws PAModelException
	{
		String uri = null;
		File outdir = new File(System.getProperty("user.dir"));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					outdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		
		PAModel m = PflowModelBuilder.Create(uri).load();
//		Map<ListMetaType, List<ColChange>> x = m.getChanges().stream().collect(Collectors.groupingBy(ColChange::getListMeta));

		
	}
}
