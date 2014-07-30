package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.ListMetaType;
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
	protected PAModelI _m;
	@Override
	public PAModel load() throws PAModelException
	{
		loadPrep();
		_m = new PAModelI(this);
		return _m;
	}

	protected abstract void loadPrep();

	protected abstract BusListI loadBuses() throws PAModelException;
	protected abstract SwitchListI loadSwitches() throws PAModelException;
	protected abstract LineListI loadLines() throws PAModelException;
	protected abstract AreaListI loadAreas() throws PAModelException;
	protected abstract OwnerListI loadOwners() throws PAModelException;
	protected abstract StationListI loadStations() throws PAModelException;
	protected abstract VoltageLevelListI loadVoltageLevels() throws PAModelException;
	protected abstract IslandList loadIslands() throws PAModelException;
	protected abstract SVCListI loadSVCs() throws PAModelException;
	protected abstract SwitchedShuntListI loadSwitchedShunts() throws PAModelException;
	protected abstract TwoTermDCLineListI loadTwoTermDCLines() throws PAModelException;
	protected abstract ShuntCapListI loadShuntCapacitors() throws PAModelException;
	protected abstract ShuntReacListI loadShuntReactors() throws PAModelException;
	protected abstract LoadListI loadLoads() throws PAModelException;
	protected abstract GenListI loadGens() throws PAModelException;
	protected abstract SeriesCapListI loadSeriesCapacitors() throws PAModelException;
	protected abstract SeriesReacListI loadSeriesReactors() throws PAModelException;
	protected abstract PhaseShifterListI loadPhaseShifters() throws PAModelException;
	protected abstract TransformerListI loadTransformers() throws PAModelException;

	protected abstract <R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys) throws PAModelException;
}
