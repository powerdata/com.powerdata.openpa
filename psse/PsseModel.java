package com.powerdata.openpa.psse;

public abstract class PsseModel
{	
	public static final float D2R = ((float)Math.PI)/180F;
	public static float deg2rad(float deg) {return deg*D2R;}
	public static float rad2deg(float rad) {return rad/D2R;}

	
	public float getSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public Bus getBus(String id) throws PsseModelException
	{
		return getBuses().get(id);
	}
	
	abstract public String getContainerName();
	abstract public BusList<?> getBuses() throws PsseModelException;
	abstract public GeneratorList<?> getGenerators() throws PsseModelException;
	abstract public NontransformerBranchList<?> getNontransformerBranches() throws PsseModelException;
	abstract public TransformerList<?> getTransformers() throws PsseModelException;
	abstract public OwnerList<?> getOwners() throws PsseModelException;
	abstract public AreaInterchangeList<?> getAreas() throws PsseModelException;
	abstract public ZoneList<?> getZones() throws PsseModelException;
	abstract public ImpCorrTblList<?> getImpCorrTables() throws PsseModelException;
}

