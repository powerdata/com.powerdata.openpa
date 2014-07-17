package com.powerdata.openpa.impl;

import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.AreaListI;
import com.powerdata.openpa.impl.BusListI;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.LineListI;
import com.powerdata.openpa.impl.LoadListI;
import com.powerdata.openpa.impl.OwnerListI;
import com.powerdata.openpa.impl.PAModelI;
import com.powerdata.openpa.impl.PhaseShifterListI;
import com.powerdata.openpa.impl.SVCListI;
import com.powerdata.openpa.impl.SeriesCapListI;
import com.powerdata.openpa.impl.SeriesReacListI;
import com.powerdata.openpa.impl.ShuntCapListI;
import com.powerdata.openpa.impl.ShuntReacListI;
import com.powerdata.openpa.impl.StationListI;
import com.powerdata.openpa.impl.SwitchListI;
import com.powerdata.openpa.impl.SwitchedShuntListI;
import com.powerdata.openpa.impl.TransformerListI;
import com.powerdata.openpa.impl.TwoTermDCLineListI;
import com.powerdata.openpa.impl.VoltageLevelListI;

/**
 * Interface for ModelBuilders, used to configure and create a PAModel object
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class ModelBuilderI implements com.powerdata.openpa.ModelBuilder
{
	@Override
	public PAModel load() throws PAModelException
	{
		PAModelI m = new PAModelI();
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

	protected void loadStop(PAModelI m) throws PAModelException {}
	protected void loadStart(PAModelI m) throws PAModelException {}

	protected abstract BusListI loadBuses(PAModelI m) throws PAModelException;
	protected abstract SwitchListI loadSwitches(PAModelI m) throws PAModelException;
	protected abstract LineListI loadLines(PAModelI m) throws PAModelException;
	protected abstract AreaListI loadAreas(PAModelI m) throws PAModelException;
	protected abstract OwnerListI loadOwners(PAModelI m) throws PAModelException;
	protected abstract StationListI loadStations(PAModelI m) throws PAModelException;
	protected abstract VoltageLevelListI loadVoltageLevels(PAModelI m) throws PAModelException;
	protected abstract IslandList loadIslands(PAModelI m) throws PAModelException;
	protected abstract SVCListI loadSVCs(PAModelI m) throws PAModelException;
	protected abstract SwitchedShuntListI loadSwitchedShunts(PAModelI m) throws PAModelException;
	protected abstract TwoTermDCLineListI loadTwoTermDCLines(PAModelI m) throws PAModelException;
	protected abstract ShuntCapListI loadShuntCapacitors(PAModelI m) throws PAModelException;
	protected abstract ShuntReacListI loadShuntReactors(PAModelI m) throws PAModelException;
	protected abstract LoadListI loadLoads(PAModelI m) throws PAModelException;
	protected abstract GenListI loadGens(PAModelI m) throws PAModelException;
	protected abstract SeriesCapListI loadSeriesCapacitors(PAModelI m) throws PAModelException;
	protected abstract SeriesReacListI loadSeriesReactors(PAModelI m) throws PAModelException;
	protected abstract PhaseShifterListI loadPhaseShifters(PAModelI m) throws PAModelException;
	protected abstract TransformerListI loadTransformers(PAModelI m) throws PAModelException;

}
