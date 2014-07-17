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
		m.loadComplete();
		return m;
	}

	protected void loadStop(PAModel m) throws PAModelException {}
	protected void loadStart(PAModel m) throws PAModelException {}

	protected abstract BusListI loadBuses(PAModel m) throws PAModelException;
	protected abstract SwitchListI loadSwitches(PAModel m) throws PAModelException;
	protected abstract LineListI loadLines(PAModel m) throws PAModelException;
	protected abstract AreaListI loadAreas(PAModel m) throws PAModelException;
	protected abstract OwnerListI loadOwners(PAModel m) throws PAModelException;
	protected abstract StationListI loadStations(PAModel m) throws PAModelException;
	protected abstract VoltageLevelListI loadVoltageLevels(PAModel m) throws PAModelException;
	protected abstract IslandList loadIslands(PAModel m) throws PAModelException;
	protected abstract SVCListI loadSVCs(PAModel m) throws PAModelException;
	protected abstract SwitchedShuntListI loadSwitchedShunts(PAModel m) throws PAModelException;
	protected abstract TwoTermDCLineListI loadTwoTermDCLines(PAModel m) throws PAModelException;
	protected abstract ShuntCapListI loadShuntCapacitors(PAModel m) throws PAModelException;
	protected abstract ShuntReacListI loadShuntReactors(PAModel m) throws PAModelException;
	protected abstract LoadListI loadLoads(PAModel m) throws PAModelException;
	protected abstract GenListI loadGens(PAModel m) throws PAModelException;
	protected abstract SeriesCapListI loadSeriesCapacitors(PAModel m) throws PAModelException;
	protected abstract SeriesReacListI loadSeriesReactors(PAModel m) throws PAModelException;
	protected abstract PhaseShifterListI loadPhaseShifters(PAModel m) throws PAModelException;
	protected abstract TransformerListI loadTransformers(PAModel m) throws PAModelException;

}
