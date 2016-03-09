package com.powerdata.openpa;

import java.util.List;

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
	
	/** return list of Steam Turbines */
	SteamTurbineList getSteamTurbines() throws PAModelException;
	
	/**
	 * get all one-terminal devices 
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all one-terminal device lists
	 */
	List<OneTermDevList> getOneTermDevices() throws PAModelException;

	/**
	 * get all two-terminal devices
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all two-terminal devices
	 */
	List<TwoTermDevList> getTwoTermDevices() throws PAModelException;

	/**
	 * get all AC Branches
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all ACBranch devices
	 */
	List<ACBranchList> getACBranches() throws PAModelException;
	
	List<FixedShuntList> getFixedShunts() throws PAModelException;

}
