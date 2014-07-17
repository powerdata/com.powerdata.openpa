package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PhaseShifterList;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.SeriesCapList;
import com.powerdata.openpa.SeriesReacList;
import com.powerdata.openpa.ShuntCapList;
import com.powerdata.openpa.ShuntReacList;
import com.powerdata.openpa.SwitchList;
import com.powerdata.openpa.SwitchedShuntList;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.TwoTermDCLineList;

public interface GroupListIfc<T extends Group> extends BaseList<T> 
{

	BusList getBuses(int ndx);

	SwitchList getSwitches(int ndx);

	LineList getLines(int ndx);

	SeriesReacList getSeriesReactors(int ndx);

	SeriesCapList getSeriesCapacitors(int ndx);

	TransformerList getTransformers(int ndx);

	PhaseShifterList getPhaseShifters(int ndx);

	TwoTermDCLineList getTwoTermDCLines(int ndx);

	GenList getGenerators(int ndx);

	LoadList getLoads(int ndx);

	ShuntReacList getShuntReactors(int ndx);

	ShuntCapList getShuntCapacitors(int ndx);

	SwitchedShuntList getSwitchedShunts(int ndx);

	SVCList getSVCs(int ndx);
	
	T getByBus(Bus b);
	
}
