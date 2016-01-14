package com.powerdata.openpa;


public interface GroupListIfc<T extends Group> extends BaseList<T>
{
	/**
	 * Get Buses contained in group
	 * @param ndx group index
	 * @return Buses contained in group
	 */
	BusList getBuses(int ndx) throws PAModelException;

	/**
	 * Get Switches contained in group
	 * @param ndx group index
	 * @return Switches contained in group
	 */
	SwitchList getSwitches(int ndx) throws PAModelException;

	/**
	 * Get Lines contained in group
	 * @param ndx group index
	 * @return Lines contained in group
	 */
	LineList getLines(int ndx) throws PAModelException;

	/**
	 * Get Series Reactors contained in group
	 * @param ndx group index
	 * @return Series Reactors contained in group
	 */
	SeriesReacList getSeriesReactors(int ndx) throws PAModelException;

	/**
	 * Get Series Capacitors contained in group
	 * @param ndx group index
	 * @return Series Capacitors contained in group
	 */
	SeriesCapList getSeriesCapacitors(int ndx) throws PAModelException;

	/**
	 * Get Transformers contained in group
	 * @param ndx group index
	 * @return Transformers contained in group
	 */
	TransformerList getTransformers(int ndx) throws PAModelException;

	/**
	 * Get Phase Shifters contained in group
	 * @param ndx group index
	 * @return Phase Shifters contained in group
	 */
	PhaseShifterList getPhaseShifters(int ndx) throws PAModelException;

	/**
	 * Get Two-Terminal DC Lines contained in group
	 * @param ndx group index
	 * @return Two-Terminal DC Lines contained in group
	 */
	TwoTermDCLineList getTwoTermDCLines(int ndx) throws PAModelException;

	/**
	 * Get Generators contained in group
	 * @param ndx group index
	 * @return Generators contained in group
	 */
	GenList getGenerators(int ndx) throws PAModelException;

	/**
	 * Get Loads contained in group
	 * @param ndx group index
	 * @return Loads contained in group
	 */
	LoadList getLoads(int ndx) throws PAModelException;

	/**
	 * Get Shunt Reactors contained in group
	 * @param ndx group index
	 * @return Shunt Reactors contained in group
	 */
	ShuntReacList getShuntReactors(int ndx) throws PAModelException;

	/**
	 * Get Shunt Capacitors contained in group
	 * @param ndx group index
	 * @return Shunt Capacitors contained in group
	 */
	ShuntCapList getShuntCapacitors(int ndx) throws PAModelException;

	/**
	 * Get Static Var Compensators contained in group
	 * @param ndx group index
	 * @return Static Var Compensators contained in group
	 */
	SVCList getSVCs(int ndx) throws PAModelException;

	/**
	 * Return a group object by Bus
	 * @param b
	 * @return
	 * @throws PAModelException
	 */
	T getByBus(Bus b) throws PAModelException;

	/** bulk convert a set of bus indexes to indexes of this list */
	int[] translateBusIndexes(int[] indexes);
}
