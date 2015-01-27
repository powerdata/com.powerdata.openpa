package com.powerdata.openpa;


public interface GroupListIfc<T extends Group> extends BaseList<T> 
{
	/**
	 * Get Buses contained in group
	 * @param ndx group index
	 * @return Buses contained in group
	 */
	BusList getBuses(int ndx) throws PAModelException;

	SwitchList getSwitches(int ndx) throws PAModelException;

	LineList getLines(int ndx) throws PAModelException;

	SeriesReacList getSeriesReactors(int ndx) throws PAModelException;

	SeriesCapList getSeriesCapacitors(int ndx) throws PAModelException;

	TransformerList getTransformers(int ndx) throws PAModelException;

	PhaseShifterList getPhaseShifters(int ndx) throws PAModelException;

	TwoTermDCLineList getTwoTermDCLines(int ndx) throws PAModelException;

	GenList getGenerators(int ndx) throws PAModelException;

	LoadList getLoads(int ndx) throws PAModelException;

	ShuntReacList getShuntReactors(int ndx) throws PAModelException;

	ShuntCapList getShuntCapacitors(int ndx) throws PAModelException;

	SVCList getSVCs(int ndx) throws PAModelException;
	
	T getByBus(Bus b) throws PAModelException;

	int[] translateBusIndexes(int[] indexes);
}
