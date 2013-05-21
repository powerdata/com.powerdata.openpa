package com.powerdata.openpa.psse;

import java.io.IOException;

public interface Container
{
	public String getContainerName();
	public BusList<?> getBuses() throws IOException;
	public GeneratorList<?> getGenerators() throws IOException;
	public NontransformerBranchList<?> getNontransformerBranches() throws IOException;
	public TransformerList<?> getTransformers() throws IOException;
}
