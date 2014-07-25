package com.powerdata.openpa;

/**
 * Standard power system equipment lists
 * 
 * @author chris@powerdata.com
 *
 */
public interface PALists
{
	/** return list of buses */
	BusList getBuses() throws PAModelException;
	
	/** return list of switches */
	SwitchList getSwitches() throws PAModelException;

	/** return list of lines */
	LineList getLines() throws PAModelException;

	/** return list of series reactors */
	SeriesReacList getSeriesReactors() throws PAModelException;
	
	/** return list of series capacitors */
	SeriesCapList getSeriesCapacitors() throws PAModelException;
	
	/** return list of transformers */
	TransformerList getTransformers() throws PAModelException;
	
	/** return list of phase shifters */
	PhaseShifterList getPhaseShifters() throws PAModelException;
	
	/** return list of two-terminal DC Lines */
	TwoTermDCLineList getTwoTermDCLines() throws PAModelException;
	
	/** return list of generators */
	GenList getGenerators() throws PAModelException;
	
	/** return list of loads */
	LoadList getLoads() throws PAModelException;
	
	/** return list of shunt reactors */
	ShuntReacList getShuntReactors() throws PAModelException;
	
	/** return list of shunt capacitors */
	ShuntCapList getShuntCapacitors() throws PAModelException;

	/** return list of SVC's */
	SVCList getSVCs() throws PAModelException;
	
}
