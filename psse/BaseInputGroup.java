package com.powerdata.openpa.psse;

public interface BaseInputGroup
{
	public BusInList getBuses() throws PsseModelException;
	public GenInList getGenerators() throws PsseModelException;
	public LoadInList getLoads() throws PsseModelException;
	public LineInList getLines() throws PsseModelException;
	public TransformerInList getTransformers() throws PsseModelException;
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException;

	public GroupInList getGroup(String type) throws PsseModelException;
	public String[] getGroupTypes();
	
	public IslandInList getIslands() throws PsseModelException;
}
