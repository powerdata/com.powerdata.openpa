package com.powerdata.openpa.padbc;

public interface Factory
{
	public ACLineList getACLines();
	public SeriesCapacitorList getSeriesCapacitors();
	public SeriesReactorList getSeriesReactors();
	public TransformerWndList getTransformerWindings();
	public PhaseShftWndList getPhaseShifterWindings();
	

//	public TwoTermDCLineList getTwoTermDCLines();
//	public MultiTermDCLineList getMultiTermDCLines();
//	public VscDCLineList getVscDCLines();
	
	/* 1-term devices */
	public GeneratorList getGenerators();
	public LoadList getLoads();
//	public SwitchedShuntList getSwitchedShunts();
//	public StaticVarCompList getStaticVarCompensators();
	
	public NodeList getNodes();
}
