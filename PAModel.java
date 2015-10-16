package com.powerdata.openpa;

import java.util.Set;

public interface PAModel extends PALists
{
	float getSBASE() throws PAModelException;
	
	/** Get a generic representation of a list based on its enumerated type 
	 * @throws PAModelException */
	BaseList<? extends BaseObject> getList(ListMetaType type) throws PAModelException;

	/** return list of switched shunts */
	SwitchedShuntList getSwitchedShunts() throws PAModelException;
	
	/** return the islands */ 
	ElectricalIslandList getElectricalIslands() throws PAModelException;
	/** refresh the island and singlebus list to reflect changes to topology or generator state */ 
	void refreshTopology() throws PAModelException;
	/** get Areas */
	AreaList getAreas() throws PAModelException;
	/** get owners */
	OwnerList getOwners() throws PAModelException;
	/** get Substations */
	StationList getStations() throws PAModelException;
	/** get Voltage Levels */
	VoltageLevelList getVoltageLevels() throws PAModelException;

	/** get Single Bus view of nodes interconnected by closed switches */
	BusList getSingleBus() throws PAModelException;
	
	GroupList createGroups(GroupIndex map);
	
	/**
	 * Get a set of changes made to the model
	 *
	 * @return Set of ColChange objects representing changes to the model
	 */
	Set<ColChange> getChanges();
	/** clear changes */
	void clearChanges();
	// TODO: Check if this is really what we want to do 
	long refresh() throws PAModelException;

}
