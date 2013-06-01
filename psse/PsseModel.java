package com.powerdata.openpa.psse;

public abstract class PsseModel implements BaseGroup
{
	/** get system base MVA */
	public abstract float getSBASE();
	/** default system base MVA */
	public float getDeftSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public Bus getBus(String id) throws PsseModelException
	{
		return getBuses().get(id);
	}
	
	public abstract ImpCorrTblList getImpCorrTables() throws PsseModelException;
	public abstract AreaList getAreas() throws PsseModelException;
	public abstract OwnerList getOwners() throws PsseModelException;
	public abstract ZoneList getZones() throws PsseModelException;
	
	
	@Override
	public IslandList getIslands() throws PsseModelException {return new IslandList(this);}
	@Override
	public GroupList getGroup(String type) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getGroupTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

