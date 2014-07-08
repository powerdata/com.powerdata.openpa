package com.powerdata.openpa;

public interface GroupList<T extends Group> extends BaseList<T> 
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
