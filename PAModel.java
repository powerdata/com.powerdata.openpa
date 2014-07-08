package com.powerdata.openpa;

/**
 * Open PA model.  Provides access to power network and equipment 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAModel implements PALists
{
	protected BusList _buslist;
	protected SwitchList _swlist;
	protected LineList _linelist;
	protected IslandList _islands;
	protected AreaList _areas;
	protected OwnerList _owners;
	protected StationList _stations;
	protected VoltageLevelList _vlevs;
	
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
		return _buslist;
	}

	@Override
	public SwitchList getSwitches()
	{
		return _swlist;
	}

	@Override
	public LineList getLines()
	{
		return _linelist;
	}

	@Override
	public SeriesReacList getSeriesReactors()
	{
		// TODO Auto-generated method stub
		return SeriesReacList.Empty;
	}

	@Override
	public SeriesCapList getSeriesCapacitors()
	{
		// TODO Auto-generated method stub
		return SeriesCapList.Empty;
	}

	@Override
	public TransformerList getTransformers()
	{
		// TODO Auto-generated method stub
		return TransformerList.Empty;
	}

	@Override
	public PhaseShifterList getPhaseShifters()
	{
		// TODO Auto-generated method stub
		return PhaseShifterList.Empty;
	}

	@Override
	public GenList getGenerators()
	{
		// TODO Auto-generated method stub
		return GenList.Empty;
	}

	@Override
	public LoadList getLoads()
	{
		// TODO Auto-generated method stub
		return LoadList.Empty;
	}

	@Override
	public ShuntReacList getShuntReactors()
	{
		// TODO Auto-generated method stub
		return ShuntReacList.Empty;
	}

	@Override
	public ShuntCapList getShuntCapacitors()
	{
		// TODO Auto-generated method stub
		return ShuntCapList.Empty;
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines()
	{
		// TODO Auto-generated method stub
		return TwoTermDCLineList.Empty;
	}

	@Override
	public SwitchedShuntList getSwitchedShunts()
	{
		return SwitchedShuntList.Empty;
	}

	@Override
	public SVCList getSVCs()
	{
		// TODO Auto-generated method stub
		return SVCList.Empty;
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
