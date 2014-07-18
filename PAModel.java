package com.powerdata.openpa;

import java.util.Set;

public interface PAModel extends PALists
{
	/** Get a generic representation of a list based on its enumerated type */
	BaseList<? extends BaseObject> getList(ListMetaType type);

	/** return the islands */
	IslandList getIslands();
	/** refresh the island list to reflect changes to topology or generator state */
	IslandList refreshIslands();
	/** get Areas */
	AreaList getAreas();
	/** get owners */
	OwnerList getOwners();
	/** get Substations */
	StationList getStations();
	/** get Voltage Levels */
	VoltageLevelList getVoltageLevels();

	/** get Single Bus view of nodes interconnected by closed switches */
	BusList getSingleBus();
	
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
