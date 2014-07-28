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
	
	/**
	 * get all one-terminal devices as a single list
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all one-terminal device lists
	 */
	OneTermDevList getOneTermDevices() throws PAModelException;

	/**
	 * get all two-terminal devices as a single list
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all two-terminal devices
	 */
	TwoTermDevList getTwoTermDevices() throws PAModelException;

	/**
	 * get all AC Branches as a single list
	 * 
	 * @throws PAModelException
	 *             Method uses reflection to find all ACBranch devices
	 */
	ACBranchList getACBranches() throws PAModelException;
	
	FixedShuntList getFixedShunts() throws PAModelException;

	/** Create arbitrary groups of buses */
	GroupList createGroups(BusGrpMap map);
	
	/**
	 * <p>
	 * Get a set of changes made to the model
	 * </p>
	 * <p>
	 * The {@link ColChange} object overrides equals to allow for comparison
	 * directly with a {@link ColumnMeta}. This allows the caller to use
	 * contains() or get() directly on the returned set rather than requiring
	 * iteration.
	 * 
	 * @return Set of ColChange objects representing changes to the model
	 */
	Set<ColChange> getChanges();
	/** clear changes */
	void clearChanges();
}
