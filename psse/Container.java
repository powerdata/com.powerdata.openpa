package com.powerdata.openpa.psse;

import java.io.IOException;

public interface Container
{
	public String getContainerName();
	public BusList<?> getBusses() throws IOException;
	public GeneratorList<?> getGenerators() throws IOException;
	public NontransformerBranchList<?> getNontransformerBranches() throws IOException;
	//public TransformerWndList<?> getTransformerWndList() throws IOException;
}
