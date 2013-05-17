package com.powerdata.openpa.padbc;

public interface Factory
{
	public ACLineList getACLines();
	public SeriesCapacitorList getSeriesCapacitors();
	public SeriesReactorList getSeriesReactors();
	public TransformerWndList getTransformerWindings();
	public PhaseShftWndList getPhaseShifterWindings();

	/** return all branches */
	public BranchList getBranches();
	
	/*
	 * TODO: Determine how DC links could be considered as a branch if the app
	 * wants a simplified view and just uses getBranches()
	 */

	// public TwoTermDCLineList getTwoTermDCLines();
	// public MultiTermDCLineList getMultiTermDCLines();
	// public VscDCLineList getVscDCLines();
	
	/* 1-term devices */
	public GeneratorList getGenerators();
	public LoadList getLoads();
	public SwitchedShuntList getSwitchedShunts();
	public StaticVarCompList getStaticVarCompensators();
	
	public NodeList getNodes();
}
