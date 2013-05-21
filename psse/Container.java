package com.powerdata.openpa.psse;

public interface Container
{
	public String getContainerName();
	public BusList<?> getBusses();
	public NontransformerBranchList<?> getNontransformerBranches();
	public TransformerList<?> getTransformers();

}
