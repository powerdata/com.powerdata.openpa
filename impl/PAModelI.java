package com.powerdata.openpa.impl;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashSet;
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
	private SwitchListI			_switches;
	private LineListI			_lines;
	private IslandList			_islands;
	private AreaListI			_areas;
	private OwnerListI			_owners;
	private StationListI 		_stations;
	private VoltageLevelListI 	_vlevs;
	private TransformerListI	_transformers;
	private PhaseShifterListI 	_phshifts;
	private SeriesReacListI 	_serreacs;
	private SeriesCapListI 		_sercaps;
	private GenListI 			_gens;
	private LoadListI 			_loads;
	private ShuntReacListI 		_shuntreacs;
	private ShuntCapListI 		_shuntcaps;
	private TwoTermDCLineListI 	_t2dclines;
	private SwitchedShuntListI 	_swshunts;
	private SVCListI 			_svcs;
	 
	ModelBuilderI _bldr;
	
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
	
	@FunctionalInterface
	interface ListConsumer<T extends BaseList<? extends BaseObject>>
	{
		 T accept() throws PAModelException;
	}
	
	
	Map<ListMetaType, ListConsumer<? extends BaseList<? extends BaseObject>>> _lists = 
			new EnumMap<>(ListMetaType.class);	
	@Override
	public BaseList<? extends BaseObject> getList(ListMetaType type) throws PAModelException
	{
		return _lists.get(type).accept();
	}
	
	@Override
	public IslandList getIslands() throws PAModelException
	{
		if (_islands == null) _islands = _bldr.loadIslands();
		return _islands;
	}
	
	@Override
	@Nodump
	public IslandList refreshIslands() throws PAModelException
	{
		_islands = _bldr.loadIslands();
		return _islands;
	}
	
	@Override
	public AreaListI getAreas() throws PAModelException
	{
		if (_areas == null) _areas = _bldr.loadAreas();
		return _areas;
	}
	
	@Override
	public OwnerListI getOwners() throws PAModelException
	{
		if (_owners == null) _owners = _bldr.loadOwners();
		return _owners;
	}
	
	@Override
	public StationListI getStations() throws PAModelException
	{
		if (_stations == null) _stations = _bldr.loadStations();
		return _stations;
	}
	
	@Override
	public VoltageLevelListI getVoltageLevels() throws PAModelException
	{
		if (_vlevs == null) _vlevs = _bldr.loadVoltageLevels();
		return _vlevs;
	}

	@Override
	public BusListI getBuses() throws PAModelException
	{
		if (_buses == null) _buses = _bldr.loadBuses();
		return _buses;
	}

	@Override
	public SwitchList getSwitches() throws PAModelException
	{
		if (_switches == null) _switches = _bldr.loadSwitches();
		return _switches;
	}

	@Override
	public LineList getLines() throws PAModelException
	{
		if (_lines == null) _lines = _bldr.loadLines();
		return _lines;
	}

	@Override
	public SeriesReacList getSeriesReactors() throws PAModelException
	{
		if (_serreacs == null) _serreacs = _bldr.loadSeriesReactors();
		return _serreacs;
	}

	@Override
	public SeriesCapList getSeriesCapacitors() throws PAModelException
	{
		if (_sercaps == null) _sercaps = _bldr.loadSeriesCapacitors();
		return _sercaps;
	}

	@Override
	public TransformerList getTransformers() throws PAModelException
	{
		if (_transformers == null)
			_transformers = _bldr.loadTransformers();
		return _transformers;
	}

	@Override
	public PhaseShifterList getPhaseShifters() throws PAModelException
	{
		if (_phshifts == null) _phshifts = _bldr.loadPhaseShifters();
		return _phshifts;
	}

	@Override
	public GenList getGenerators() throws PAModelException
	{
		if (_gens == null) _gens = _bldr.loadGens();
		return _gens;
	}

	@Override
	public LoadList getLoads() throws PAModelException
	{
		if (_loads == null) _loads = _bldr.loadLoads();
		return _loads;
	}

	@Override
	public ShuntReacList getShuntReactors() throws PAModelException
	{
		if (_shuntreacs == null) _shuntreacs = _bldr.loadShuntReactors();
		return _shuntreacs;
	}

	@Override
	public ShuntCapList getShuntCapacitors() throws PAModelException
	{
		if (_shuntcaps == null) _shuntcaps = _bldr.loadShuntCapacitors();
		return _shuntcaps;
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines() throws PAModelException
	{
		if (_t2dclines == null) _t2dclines = _bldr.loadTwoTermDCLines();
		return _t2dclines;
	}

	@Override
	public SwitchedShuntList getSwitchedShunts() throws PAModelException
	{
		if (_swshunts == null) _swshunts = _bldr.loadSwitchedShunts();
		return _swshunts;
	}

	@Override
	public SVCList getSVCs() throws PAModelException
	{
		if (_svcs == null) _svcs = _bldr.loadSVCs();
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
	public BusList getSingleBus() throws PAModelException
	{
		return new SingleBusList(this);
	}

	@Override
	public Set<OneTermDevList> getOneTermDevices() throws PAModelException
	{
		return getSet(OneTermDevList.class);
	}

	@Override
	public Set<TwoTermDevList> getTwoTermDevices() throws PAModelException
	{
		return getSet(TwoTermDevList.class);
	}

	@Override
	public Set<ACBranchList> getACBranches() throws PAModelException
	{
		return getSet(ACBranchList.class);
	}

	@Override
	public GroupList createGroups(BusGrpMap map)
	{
		return new GroupList(this, map);
	}

	<R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys) throws PAModelException
	{
		return _bldr.load(ltype, ctype, keys);
	}

	@Override
	public Set<FixedShuntList> getFixedShunts() throws PAModelException
	{
		return getSet(FixedShuntList.class);
	}
	
	@SuppressWarnings("unchecked")
	<T extends BaseList<? extends BaseObject>> Set<T> getSet(Class<T> clz) throws PAModelException
	{
		Set<T> rv = new HashSet<>();
		try
		{
			for (Method m : getClass().getMethods())
			{
				Class<?> rt = m.getReturnType();
				if (!rt.equals(this.getClass()) && testInterfaces(clz, rt))
				{
					T list;
					list = (T) m.invoke(this, new Object[] {});
					if (!list.isEmpty())
						rv.add(list);
				}
			}
		}
		catch (ReflectiveOperationException | IllegalArgumentException e)
		{
			throw new PAModelException(e);
		}
		return rv;
	}
	
	boolean testInterfaces(Class<?> clz, Class<?> returnType)
	{
		if (returnType.equals(clz))
			return true;
		else if (returnType.equals(BaseList.class))
			return false;
		else
		{
			for (Class<?> pifc : returnType.getInterfaces())
				if (testInterfaces(clz, pifc)) return true;
		}
		return false;
	}

}
