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
	 * @throws RuntimeException
	 *             Method uses reflection to find all one-terminal device lists
	 * @throws ReflectiveOperationException
	 *             Method uses reflection to find all one-terminal device lists
	 */
	OneTermDevList getOneTermDevices() throws ReflectiveOperationException,
			RuntimeException;

	/**
	 * get all two-terminal devices as a single list
	 * 
	 * @throws RuntimeException
	 *             Method uses reflection to find all two-terminal devices
	 * @throws ReflectiveOperationException
	 *             Method uses reflection to find all one-terminal device lists
	 */
	TwoTermDevList getTwoTermDevices() throws ReflectiveOperationException,
			RuntimeException;

	/**
	 * get all AC Branches as a single list
	 * 
	 * @throws RuntimeException
	 *             Method uses reflection to find all ACBranch devices
	 * @throws ReflectiveOperationException
	 *             Method uses reflection to find all ACBranch devices
	 */
	ACBranchList getACBranches() throws ReflectiveOperationException, RuntimeException;

	/** Create arbitrary groups of buses */
	GroupList createGroups(BusGrpMap map);
	
	/** get changes */
	Set<ColChange> getChanges();
	/** clear changes */
	void clearChanges();
}
