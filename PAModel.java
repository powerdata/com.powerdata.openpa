package com.powerdata.openpa;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		ListMetaType _lmeta;
		int[] _idx;
		int _size;
		private AbstractPAList<? extends BaseObject>.Data _d;
		
		
		ColChange(ListMetaType lmeta,
				AbstractPAList<? extends BaseObject>.Data d)
		{
			_lmeta = lmeta;
			_d = d;
		}		
		public ColumnMeta getColMeta() {return _d.getColMeta();}
		public ListMetaType getListMeta() {return _lmeta;}
		public int[] getNdxs()
		{
			if (_idx == null)
			{
				_idx = _d.computeChanges();
				_size = _idx.length;
			}
			return _idx;
		}
		
		public int[] getKeys()
		{
			return _d.getKeys(getNdxs());
		}
		
		public int size() {return _size;}
		
		public abstract String[] stringAccess();
		public abstract float[] floatAccess();
		public abstract int[] intAccess();
		public abstract boolean[] boolAccess();

		@Override
		public boolean equals(Object obj)
		{
			return getColMeta() == ((ColChange)obj).getColMeta();
		}
	}
	
	public static class IntColChange extends ColChange
	{
		AbstractPAList<? extends BaseObject>.IntDataIfc _d;
		int[] _vals;
		
		IntColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.IntDataIfc d)
		{
			super(lmeta, d);
			_d = d;
		}

		@Override
		public String[] stringAccess()
		{
			int[] ival = intAccess();
			String[] rv = new String[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = String.valueOf(ival[i]);
			return rv;
		}

		@Override
		public float[] floatAccess()
		{
			int[] ival = intAccess();
			float[] rv = new float[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = (float) ival[i];
			return rv;
		}

		@Override
		public int[] intAccess()
		{
			if (_vals == null)
				_vals = _d.getInts(getNdxs());
			return _vals;
		}

		@Override
		public boolean[] boolAccess()
		{
			int[] ival = intAccess();
			boolean[] rv = new boolean[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = ival[i] != 0;
			return rv;
		}
	}

	public static class FloatColChange extends ColChange
	{
		AbstractPAList<? extends BaseObject>.FloatData _d;
		private float[] _vals;
		
		FloatColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.FloatData d)
		{
			super(lmeta, d);
			_d = d;
		}

		@Override
		public String[] stringAccess()
		{
			float[] ival = floatAccess();
			String[] rv = new String[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = String.valueOf(ival[i]);
			return rv;
		}

		@Override
		public float[] floatAccess()
		{
			if (_vals == null)
				_vals = _d.getFloats(getNdxs());
			return _vals;
		}

		@Override
		public int[] intAccess()
		{
			float[] val = floatAccess();
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = Math.round(val[i]);
			return rv;
		}

		@Override
		public boolean[] boolAccess()
		{
			float[] val = floatAccess();
			boolean[] rv = new boolean[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = val[i] != 0f;
			return rv;
		}

	}
	
	public static class StringColChange extends ColChange
	{
		AbstractPAList<? extends BaseObject>.StringData _d;
		private String[] _vals;

		StringColChange(ListMetaType lmeta,
				AbstractPAList<? extends BaseObject>.StringData d)
		{
			super(lmeta, d);
			_d = d;
		}

		@Override
		public String[] stringAccess()
		{
			if (_vals == null)
				_vals = _d.getStrings(getNdxs());
			return _vals;
		}

		@Override
		public float[] floatAccess()
		{
			String[] val = stringAccess();
			float[] rv = new float[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = Float.parseFloat(val[i]);
			return rv;
		}

		@Override
		public int[] intAccess()
		{
			String[] val = stringAccess();
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = Integer.parseInt(val[i]);
			return rv;
		}

		@Override
		public boolean[] boolAccess()
		{
			String[] val = stringAccess();
			boolean[] rv = new boolean[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = Boolean.parseBoolean(val[i]);
			return rv;
		}
		
	}

	
	
	public static class BoolColChange extends ColChange
	{
		AbstractPAList<? extends BaseObject>.BoolData _d;
		private boolean[] _vals;
		
		BoolColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.BoolData d)
		{
			super(lmeta, d);
			_d = d;
		}

		@Override
		public String[] stringAccess()
		{
			boolean[] ival = boolAccess();
			String[] rv = new String[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = String.valueOf(ival[i]);
			return rv;
		}

		@Override
		public float[] floatAccess()
		{
			boolean[] ival = boolAccess();
			float[] rv = new float[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = ival[i] ? 1f : 0f;
			return rv;
		}

		@Override
		public int[] intAccess()
		{
			boolean[] val = boolAccess();
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i)
				rv[i] = val[i] ? 1 : 0;
			return rv;
		}

		@Override
		public boolean[] boolAccess()
		{
			if (_vals == null)
				_vals = _d.getBools(getNdxs());
			return _vals;
		}
		
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
		_lists.forEach((t,l) -> l.clearChanges());
		_changedCols.clear();
	}

	public static void main(String... args) throws PAModelException
	{
		String uri = null;
//		File outdir = new File(System.getProperty("user.dir"));
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
//				case "outdir":
//					outdir = new File(args[i++]);
//					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri); 
		
		PAModel m = bldr.load();
		m.getBuses().stream().filter(b -> ((b.getIndex() % 2) == 0))
				.forEach(b -> b.setVM(-1f));		

		for(ColChange o : m.getChanges())
		{
				System.out.format("%s %s\n", o.getListMeta(), o.getColMeta());
				int[] ndx = o.getNdxs(), keys = o.getKeys();
				String[] v = o.stringAccess();
				for(int i=0; i < o.size(); ++i)
					System.out.format("\t%d %d %s\n", ndx[i], keys[i], v[i]);
		}
		
		m = bldr.load();
		m.getBuses().stream().filter(b -> ((b.getIndex() % 2) == 0))
				.forEach(b -> b.setVM(-1f));		

		for(ColChange o : m.getChanges())
		{
				System.out.format("%s %s\n", o.getListMeta(), o.getColMeta());
				int[] ndx = o.getNdxs(), keys = o.getKeys();
				String[] v = o.stringAccess();
				for(int i=0; i < o.size(); ++i)
					System.out.format("\t%d %d %s\n", ndx[i], keys[i], v[i]);
		}
		
	}
}
