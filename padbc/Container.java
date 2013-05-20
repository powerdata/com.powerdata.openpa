package com.powerdata.openpa.padbc;

import java.io.IOException;

public interface Container
{
	public String getContainerName();
	
	/* Equipment lists */
	
	public ACLineList<?> getACLines();
	public SeriesCapacitorList<?> getSeriesCapacitors();
	public SeriesReactorList<?> getSeriesReactors();
	public TransformerWndList<?> getTransformerWindings() throws IOException;
	public PhaseShftWndList<?> getPhaseShifterWindings();
	public GeneratorList<?> getGenerators() throws IOException;
	public LoadList<?> getLoads();
	public SwitchedShuntList<?> getSwitchedShunts();
	public StaticVarCompList<?> getStaticVarCompensators();
	public NodeList<?> getNodes() throws IOException;

	/** return all branches as a single list*/
	public BranchList<?> getBranches() throws IOException;
	
	/*
	 * TODO: Determine how DC links could be considered as a branch if the app
	 * wants a simplified view and just uses getBranches()
	 */

	// public TwoTermDCLineList getTwoTermDCLines();
	// public MultiTermDCLineList getMultiTermDCLines();
	// public VscDCLineList getVscDCLines();

	public AreaList<?> getAreas();
	public OwnerList<?> getOwners();
	public StationList<?> getStations();
	public VoltageLevelList<?> getVoltageLevels();
	public TopNodeList<?> getTopNodes();
}
