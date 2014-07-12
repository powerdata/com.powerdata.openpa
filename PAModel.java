package com.powerdata.openpa;

import java.util.HashSet;
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
		Area, Owner, Station, VoltageLevel, Island, Bus, Switch, Line, 
		SeriesCap, SeriesReac, Transformer, PhaseShifter, TwoTermDCLine, 
		Gen, Load, ShuntReac, ShuntCap, SVC, SwitchedShunt
	}
	
	BusList 			_buses;
	SwitchList 			_switches;
	LineList 			_lines;
	IslandList 			_islands;
	AreaList 			_areas;
	OwnerList 			_owners;
	StationList 		_stations;
	VoltageLevelList 	_vlevs;
	TransformerList 	_transformers;
	PhaseShifterList 	_phshifts;
	SeriesReacList 		_serreacs;
	SeriesCapList 		_sercaps;
	GenList 			_gens;
	LoadList 			_loads;
	ShuntReacList 		_shuntreacs;
	ShuntCapList 		_shuntcaps;
	TwoTermDCLineList 	_t2dclines;
	SwitchedShuntList 	_swshunts;
	SVCList 			_svcs;
	 
	
	protected PAModel(){}
	
	static public class PACol
	{
		
	}
	
	static public class ListChg
	{
		ListMetaType _type;
		
	}
	
//	Set<ListChg<? extends BaseObject>> _chg;
	
	/** call for an event */
	public Set<ListChg> getChanges()
	{
		//TODO:
		return new HashSet<>(0);
	}
	
	public void clearChanges()
	{
		//TODO: implement this
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
