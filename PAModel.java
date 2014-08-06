package com.powerdata.openpa;

import java.util.Set;

public interface PAModel extends PALists
{
	/** Get a generic representation of a list based on its enumerated type 
	 * @throws PAModelException */
	BaseList<? extends BaseObject> getList(ListMetaType type) throws PAModelException;

	/** return list of switched shunts */
	SwitchedShuntList getSwitchedShunts() throws PAModelException;
	
	/** return the islands */ 
	IslandList getIslands() throws PAModelException;
	/** refresh the island list to reflect changes to topology or generator state */ 
	IslandList refreshIslands() throws PAModelException;
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
	
	GroupList createGroups(BusGrpMap map);
	
	/**
	 * Get a set of changes made to the model
	 *
	 * @return Set of ColChange objects representing changes to the model
	 */
	Set<ColChange> getChanges();
	/** clear changes */
	void clearChanges();
}
