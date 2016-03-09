package com.powerdata.openpa.impl;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.*;
import com.powerdata.openpa.ColChange;

/**
 * Open PA model.  Provides access to power network and equipment 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAModelI implements PAModel
{
	private BusListI 			_buses;
	private SwitchList			_switches;
	private LineList			_lines;
	private ElectricalIslandList			_islands;
	private AreaList			_areas;
	private OwnerList			_owners;
	private StationList 		_stations;
	private VoltageLevelList 	_vlevs;
	private TransformerList	_transformers;
	private PhaseShifterList 	_phshifts;
	private SeriesReacList 	_serreacs;
	private SeriesCapList 		_sercaps;
	private GenList 			_gens;
	private LoadList 			_loads;
	private ShuntReacList 		_shuntreacs;
	private ShuntCapList 		_shuntcaps;
	private TwoTermDCLineList 	_t2dclines;
	private SwitchedShuntList 	_swshunts;
	private SVCList 			_svcs;
	private SingleBusList		_sbus = null;
	 
	ModelBuilderI _bldr;
	
	public ModelBuilderI getBuilder() {return _bldr;}
	
	PAModelI(ModelBuilderI bldr)
	{
		_bldr = bldr;
		_lists.put(ListMetaType.Area, () -> getAreas());
		_lists.put(ListMetaType.Owner, () -> getOwners());
		_lists.put(ListMetaType.Station, () -> getStations());
		_lists.put(ListMetaType.VoltageLevel, () -> getVoltageLevels());
		_lists.put(ListMetaType.Bus, () -> getBuses());
		_lists.put(ListMetaType.Switch, () -> getSwitches());
		_lists.put(ListMetaType.Line, () -> getLines());
		_lists.put(ListMetaType.SeriesCap, () -> getSeriesCapacitors());
		_lists.put(ListMetaType.SeriesReac, () -> getSeriesReactors());
		_lists.put(ListMetaType.Transformer, () -> getTransformers());
		_lists.put(ListMetaType.PhaseShifter, () -> getPhaseShifters());
		_lists.put(ListMetaType.TwoTermDCLine, () -> getTwoTermDCLines());
		_lists.put(ListMetaType.Gen, () -> getGenerators());
		_lists.put(ListMetaType.Load, () -> getLoads());
		_lists.put(ListMetaType.ShuntReac, () -> getShuntReactors());
		_lists.put(ListMetaType.ShuntCap, () -> getShuntCapacitors());
		_lists.put(ListMetaType.SVC, () -> getSVCs());
		_lists.put(ListMetaType.SwitchedShunt, () -> getSwitchedShunts());
	}

	SuperList _slist = new SuperList(this);
	ArrayList<BaseList<? extends BaseObject>> _created = new ArrayList<BaseList<? extends BaseObject>>();
	
	@FunctionalInterface
	interface ListConsumer<T extends BaseList<? extends BaseObject>>
	{
		 T accept() throws PAModelException;
	}
	
	
	Map<ListMetaType, ListConsumer<? extends BaseList<? extends BaseObject>>> _lists = 
			new EnumMap<>(ListMetaType.class);
	SteamTurbineList _sturb;
	
	@Override
	public BaseList<? extends BaseObject> getList(ListMetaType type) throws PAModelException
	{
		return _lists.get(type).accept();
	}
	
	@Override
	public ElectricalIslandList getElectricalIslands() throws PAModelException
	{
		if (_islands == null) _islands = _bldr.loadIslands();
		return _islands;
	}
	
	@Override
	@Nodump
	public void refreshTopology() throws PAModelException
	{
		_islands = null;
		_sbus = null;
	}
	
	@Override
	public AreaList getAreas() throws PAModelException
	{
		if (_areas == null)
		{
			_areas = _bldr.loadAreas();
			_created.add(_areas);
		}
		return _areas;
	}
	
	@Override
	public OwnerList getOwners() throws PAModelException
	{
		if (_owners == null) _owners = _bldr.loadOwners();
		return _owners;
	}
	
	@Override
	public StationList getStations() throws PAModelException
	{
		if (_stations == null)
		{
			_stations = _bldr.loadStations();
			_created.add(_stations);
		}
		return _stations;
	}
	
	@Override
	public VoltageLevelList getVoltageLevels() throws PAModelException
	{
		if (_vlevs == null)
		{
			_vlevs = _bldr.loadVoltageLevels();
			_created.add(_vlevs);
		}
		return _vlevs;
	}

	@Override
	public BusListI getBuses() throws PAModelException
	{
		if (_buses == null)
		{
			_buses = _bldr.loadBuses();
			_created.add(_buses);
		}
		return _buses;
	}

	@Override
	public SwitchList getSwitches() throws PAModelException
	{
		if (_switches == null)
		{
			_switches = _bldr.loadSwitches();
			_created.add(_switches);
		}
		return _switches;
	}

	@Override
	public LineList getLines() throws PAModelException
	{
		if (_lines == null)
		{
			_lines = _bldr.loadLines();
			_created.add(_lines);
		}
		return _lines;
	}

	@Override
	public SeriesReacList getSeriesReactors() throws PAModelException
	{
		if (_serreacs == null)
		{
			_serreacs = _bldr.loadSeriesReactors();
			_created.add(_serreacs);
		}
		return _serreacs;
	}

	@Override
	public SeriesCapList getSeriesCapacitors() throws PAModelException
	{
		if (_sercaps == null)
		{
			_sercaps = _bldr.loadSeriesCapacitors();
			_created.add(_sercaps);
		}
		return _sercaps;
	}

	@Override
	public TransformerList getTransformers() throws PAModelException
	{
		if (_transformers == null)
		{
			_transformers = _bldr.loadTransformers();
			_created.add(_transformers);
		}
		return _transformers;
	}

	@Override
	public PhaseShifterList getPhaseShifters() throws PAModelException
	{
		if (_phshifts == null)
		{
			_phshifts = _bldr.loadPhaseShifters();
			_created.add(_phshifts);
		}
		return _phshifts;
	}

	@Override
	public GenList getGenerators() throws PAModelException
	{
		if (_gens == null)
		{
			_gens = _bldr.loadGens();
			_created.add(_gens);
		}
		return _gens;
	}

	@Override
	public LoadList getLoads() throws PAModelException
	{
		if (_loads == null)
		{
			_loads = _bldr.loadLoads();
			_created.add(_loads);
		}
		return _loads;
	}

	@Override
	public SteamTurbineList getSteamTurbines() throws PAModelException
	{
		if (_sturb == null)
		{
			_sturb = _bldr.loadSteamTurbines();
			_created.add(_sturb);
		}
		return _sturb;
	}

	
	@Override
	public ShuntReacList getShuntReactors() throws PAModelException
	{
		if (_shuntreacs == null)
		{
			_shuntreacs = _bldr.loadShuntReactors();
			_created.add(_shuntreacs);
		}
		return _shuntreacs;
	}

	@Override
	public ShuntCapList getShuntCapacitors() throws PAModelException
	{
		if (_shuntcaps == null)
		{
			_shuntcaps = _bldr.loadShuntCapacitors();
			_created.add(_shuntcaps);
		}
		return _shuntcaps;
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines() throws PAModelException
	{
		if (_t2dclines == null)
		{
			_t2dclines = _bldr.loadTwoTermDCLines();
			_created.add(_t2dclines);
		}
		return _t2dclines;
	}

	@Override
	public SwitchedShuntList getSwitchedShunts() throws PAModelException
	{
		if (_swshunts == null)
		{
			_swshunts = _bldr.loadSwitchedShunts();
			_created.add(_swshunts);
		}
		return _swshunts;
	}

	@Override
	public SVCList getSVCs() throws PAModelException
	{
		if (_svcs == null)
		{
			_svcs = _bldr.loadSVCs();
			_created.add(_svcs);
		}
		return _svcs;
	}
	
	Set<ColChange> _changedCols = new HashSet<>();
	
	/** call for an event */
	@Override
	public Set<ColChange> getChanges()
	{
		return _changedCols;
	}
	
	@Override
	public void clearChanges()
	{
		for(ColChange c : _changedCols) c.clear();
		_changedCols.clear();
	}
	
	void setChange(ColChange c)
	{
		_changedCols.add(c);
	}
	
	@Override
	public SingleBusList getSingleBus() throws PAModelException
	{
		if (_sbus == null)
		{
			_sbus = new SingleBusList(this);
		}
		return _sbus;
	}

	@Override
	public List<OneTermDevList> getOneTermDevices() throws PAModelException
	{
		return _slist.getOneTermDevs();
	}

	@Override
	public List<TwoTermDevList> getTwoTermDevices() throws PAModelException
	{
		return _slist.getTwoTermDevs();
	}

	@Override
	public List<ACBranchList> getACBranches() throws PAModelException
	{
		return _slist.getACBranches();
	}

	@Override
	public List<FixedShuntList> getFixedShunts() throws PAModelException
	{
		return _slist.getFixedShunts();
	}
	
	@Override
	public GroupList createGroups(GroupIndex map)
	{
		return new GroupList(this, map);
	}

	<R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys) throws PAModelException
	{
		return _bldr.load(ltype, ctype, keys);
	}

	@Override
	public float getSBASE() throws PAModelException
	{
		return 100f;
	}

	@Override
	public long refresh() throws PAModelException
	{
		long sn = 0;
		// reset all devices list
		for(BaseList<? extends BaseObject> b : _created) b.reset();
		// refresh the islands if they exist
		refreshTopology();
		
		return sn;
	}
	
	public long refreshremove() throws PAModelException
	{
		long sn = 0;
	
		// TODO: Reset all the data
		System.out.println("PAModel.refresh");
		if(_buses != null) _buses.reset();
		if(_switches!= null) _switches.reset();
		if(_lines!= null) _lines.reset();
		// if(_islands!= null) _islands.reset();
		if(_areas != null) _areas.reset();
		//if(_owners != null) _owners.reset();
		if(_stations != null) _stations.reset();
		if(_vlevs != null) _vlevs.reset();
		if(_transformers != null) _transformers.reset();
		if(_phshifts != null) _phshifts.reset();
		if(_serreacs != null) _serreacs.reset();
		if(_sercaps != null) _sercaps.reset();
		if(_gens != null) _gens.reset();
		if(_loads != null) _loads.reset();
		if(_shuntreacs != null) _shuntreacs.reset();
		if(_shuntcaps != null) _shuntcaps.reset();
		if(_t2dclines != null) _t2dclines.reset();
		if(_swshunts != null) _swshunts.reset();
		if(_svcs != null) _svcs.reset();
		
		return sn;
	}

}
