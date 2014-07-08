package com.powerdata.openpa;

/**
 * Open PA model.  Provides access to power network and equipment 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAModel implements PALists
{
	protected BusList _buses;
	protected SwitchList _switches;
	protected LineList _lines;
	protected IslandList _islands;
	protected AreaList _areas;
	protected OwnerList _owners;
	protected StationList _stations;
	protected VoltageLevelList _vlevs;
	protected TransformerList _transformers;
	protected PhaseShifterList _phshifts;
	protected SeriesReacList _serreacs;
	protected SeriesCapList _sercaps;
	protected GenList _gens;
	protected LoadList _loads;
	protected ShuntReacList _shuntreacs;
	protected ShuntCapList _shuntcaps;
	protected TwoTermDCLineList _t2dclines;
	protected SwitchedShuntList _swshunts;
	protected SVCList _svcs;
	
	protected PAModel(){}
	
	/** call for an event */
	public void processChanges(ModelEventListener l)
	{
		
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
}
