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
import com.powerdata.openpa.*;

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
	protected abstract SwitchList loadSwitches() throws PAModelException;
	protected abstract LineList loadLines() throws PAModelException;
	protected abstract AreaList loadAreas() throws PAModelException;
	protected abstract OwnerList loadOwners() throws PAModelException;
	protected abstract StationList loadStations() throws PAModelException;
	protected abstract VoltageLevelList loadVoltageLevels() throws PAModelException;
	protected abstract IslandList loadIslands() throws PAModelException;
	protected abstract SVCList loadSVCs() throws PAModelException;
	protected abstract SwitchedShuntList loadSwitchedShunts() throws PAModelException;
	protected abstract TwoTermDCLineList loadTwoTermDCLines() throws PAModelException;
	protected abstract ShuntCapList loadShuntCapacitors() throws PAModelException;
	protected abstract ShuntReacList loadShuntReactors() throws PAModelException;
	protected abstract LoadList loadLoads() throws PAModelException;
	protected abstract GenList loadGens() throws PAModelException;
	protected abstract SeriesCapList loadSeriesCapacitors() throws PAModelException;
	protected abstract SeriesReacList loadSeriesReactors() throws PAModelException;
	protected abstract PhaseShifterList loadPhaseShifters() throws PAModelException;
	protected abstract TransformerList loadTransformers() throws PAModelException;

	protected abstract <R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys) throws PAModelException;
}
