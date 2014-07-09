package com.powerdata.openpa;

/**
 * Interface for ModelBuilders, used to configure and create a PAModel object
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class ModelBuilder
{
	public PAModel load() throws PAModelException
	{
		PAModel m = new PAModel();
		loadStart(m);
		m._areas = loadAreas(m);
		m._owners = loadOwners(m);
		m._stations = loadStations(m);
		m._vlevs = loadVoltageLevels(m);
		m._buses = loadBuses(m);
		m._switches = loadSwitches(m);
		m._lines = loadLines(m);
		m._transformers = loadTransformers(m);
		m._phshifts = loadPhaseShifters(m);
		m._serreacs = loadSeriesReactors(m);
		m._sercaps = loadSeriesCapacitors(m);
		m._gens = loadGens(m);
		m._loads = loadLoads(m);
		m._shuntreacs = loadShuntReactors(m);
		m._shuntcaps = loadShuntCapacitors(m);
		m._t2dclines = loadTwoTermDCLines(m);
		m._swshunts = loadSwitchedShunts(m);
		m._svcs = loadSVCs(m);
		m._islands = loadIslands(m);
		loadStop(m);
		return m;
	}

	protected void loadStop(PAModel m) throws PAModelException {}
	protected void loadStart(PAModel m) throws PAModelException {}

	protected abstract BusList loadBuses(PAModel m) throws PAModelException;
	protected abstract SwitchList loadSwitches(PAModel m) throws PAModelException;
	protected abstract LineList loadLines(PAModel m) throws PAModelException;
	protected abstract AreaList loadAreas(PAModel m) throws PAModelException;
	protected abstract OwnerList loadOwners(PAModel m) throws PAModelException;
	protected abstract StationList loadStations(PAModel m) throws PAModelException;
	protected abstract VoltageLevelList loadVoltageLevels(PAModel m) throws PAModelException;
	protected abstract IslandList loadIslands(PAModel m) throws PAModelException;
	protected abstract SVCList loadSVCs(PAModel m) throws PAModelException;
	protected abstract SwitchedShuntList loadSwitchedShunts(PAModel m);
	protected abstract TwoTermDCLineList loadTwoTermDCLines(PAModel m);
	protected abstract ShuntCapList loadShuntCapacitors(PAModel m);
	protected abstract ShuntReacList loadShuntReactors(PAModel m);
	protected abstract LoadList loadLoads(PAModel m);
	protected abstract GenList loadGens(PAModel m) throws PAModelException;
	protected abstract SeriesCapList loadSeriesCapacitors(PAModel m) throws PAModelException;
	protected abstract SeriesReacList loadSeriesReactors(PAModel m) throws PAModelException;
	protected abstract PhaseShifterList loadPhaseShifters(PAModel m);
	protected abstract TransformerList loadTransformers(PAModel m);

}
