package com.powerdata.openpa.psse;

public abstract class PsseModel
{
	public float getSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public Bus getBus(String id) throws IOException
	{
		return getBuses().get(id);
	}
	
	abstract public String getContainerName();
	abstract public BusList<?> getBuses() throws PsseModelException;
	abstract public GeneratorList<?> getGenerators() throws PsseModelException;
	abstract public NontransformerBranchList<?> getNontransformerBranches() throws PsseModelException;
	abstract public TransformerList<?> getTransformers() throws PsseModelException;
	abstract public OwnerList<?> getOwners() throws IOException;
	abstract public AreaInterchangeList<?> getAreas() throws IOException;
	abstract public ZoneList<?> getZones() throws IOException;
}

