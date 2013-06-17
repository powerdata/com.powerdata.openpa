package com.powerdata.openpa.psse;

public abstract class PsseInputModel extends PsseModel implements BaseInputGroup
{
	/** get system base MVA */
	public abstract float getSBASE();
	/** default system base MVA */
	public float getDeftSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public BusIn getBus(String id) throws PsseModelException {return getBuses().get(id);}

	@Override
	public IslandInList getIslands() throws PsseModelException {return new IslandInList(this);}
	@Override
	public GroupInList getGroup(String type) throws PsseModelException
	{
		//TODO:
		return null;
		
	}
	@Override
	public String[] getGroupTypes() {return GroupType.values();}
	
	public abstract ImpCorrTblInList getImpCorrTables() throws PsseModelException;
	public abstract AreaInList getAreas() throws PsseModelException;
	public abstract OwnerInList getOwners() throws PsseModelException;
	public abstract ZoneInList getZones() throws PsseModelException;
	

	
}
