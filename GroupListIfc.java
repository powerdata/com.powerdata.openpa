package com.powerdata.openpa;


public interface GroupListIfc<T extends Group> extends BaseList<T> 
{
	/**
	 * Get Buses contained in group
	 * @param ndx group index
	 * @return Buses contained in group
	 */
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
