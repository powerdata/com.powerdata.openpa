package com.powerdata.openpa;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

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
		Area, Owner, Station, VoltageLevel, Island, Bus, Switch, Line, 
		SeriesCap, SeriesReac, Transformer, PhaseShifter, TwoTermDCLine, 
		Gen, 
		Load, ShuntReac, ShuntCap, SVC, SwitchedShunt
		
	}
	
	public interface ListEnum {}
	
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
	
	public OneTermDevList<? extends OneTermDev> getOneTermDevs()
	{
		
		return OneTermDevList.Empty;
	}

	public TwoTermDevList<? extends TwoTermDev> getTwoTermDevs()
	{
		
		return TwoTermDevList.Empty;
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
	
	public class ColAccess
	{
		String _name;
		int[] _ofs;
		
		IntFunction<String> _sf;
		IntFloatFunction _flt;
		IntIntFunction _int;

		public ColAccess(int[] ofs, IntFunction<String> f)
		{
			_sf = f;
			_flt = i -> Float.parseFloat(f.apply(i));
			_int = i -> Integer.parseInt(f.apply(i));
		}
		
		public ColAccess(IntIntFunction f)
		{
			_int = f;
			_flt = i -> (float) f.apply(i);
			_sf = i -> String.valueOf(f.apply(i));
		}
		
		public ColAccess(IntFloatFunction f)
		{
			_flt = f;
			_int = i -> Math.round(f.apply(i));
			_sf = i -> String.valueOf(f.apply(i));
		}
		
		public ColAccess(IntFunction<Enum<?>> f)
		{
			_flt = i -> (float) f.apply(i).ordinal();
			_int = i -> f.apply(i).ordinal();
			_sf = i -> f.apply(i).toString();
		}
		
	}
	
	Set<ListMetaType> _changedLists = EnumSet.noneOf(ListMetaType.class);
	
	protected void setChange(ListMetaType mtype)
	{
		_changedLists.add(mtype);
	}
	
	/** call for an event */
	public Set<ColAccess> getChanges()
	{
		//TODO:
		return new HashSet<>(0);

	}
	
	public void clearChanges()
	{
		_changedLists.stream().map(l -> _lists.get(l)).forEach(o -> o.clearChanges());
		_changedLists.clear();
	}


	
}
