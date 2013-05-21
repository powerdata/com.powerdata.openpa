package com.powerdata.openpa.psse;

import java.io.IOException;

public abstract class PsseModel
{
	public float getSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public Bus getBus(String id) throws IOException
	{
		return getBuses().get(id);
	}
	
	abstract public String getContainerName();
	abstract public BusList<?> getBuses() throws IOException;
	abstract public GeneratorList<?> getGenerators() throws IOException;
	abstract public NontransformerBranchList<?> getNontransformerBranches() throws IOException;
	abstract public TransformerList<?> getTransformers() throws IOException;
	
	abstract public OwnerList<?> getOwners() throws IOException;
	abstract public AreaInterchangeList<?> getAreas() throws IOException;
	abstract public ZoneList<?> getZones() throws IOException;
}

