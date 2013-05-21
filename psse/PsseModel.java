package com.powerdata.openpa.psse;

import java.io.IOException;

public abstract class PsseModel
{
	public float getSBASE() {return 100F;}
	
	abstract public String getContainerName();
	abstract public BusList<?> getBuses() throws IOException;
	abstract public GeneratorList<?> getGenerators() throws IOException;
	abstract public NontransformerBranchList<?> getNontransformerBranches() throws IOException;
	abstract public TransformerList<?> getTransformers() throws IOException;
}

