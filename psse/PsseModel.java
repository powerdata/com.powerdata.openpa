package com.powerdata.openpa.psse;

public abstract class PsseModel
{
	public float getSBASE() {return 100F;}
	
	abstract public String getContainerName();
	abstract public BusList<?> getBuses() throws PsseModelException;
	abstract public GeneratorList<?> getGenerators() throws PsseModelException;
	abstract public NontransformerBranchList<?> getNontransformerBranches() throws PsseModelException;
	abstract public TransformerList<?> getTransformers() throws PsseModelException;
}

