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
	
	/** get all one-terminal devices as a single list */
	OneTermDevList<? extends OneTermDev> getOneTermDevices();
	/** get all two-terminal devices as a single list */
	TwoTermDevList<? extends TwoTermDev> getTwoTermDevices();
	/** get all AC Branches as a single list */
	ACBranchList<? extends ACBranch> getACBranches();

	/** Create arbitrary groups of buses */
	GroupList createGroups(BusGrpMap map);
	
	/** get changes */
	Set<ColChange> getChanges();
}
