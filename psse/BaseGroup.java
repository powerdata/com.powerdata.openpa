package com.powerdata.openpa.psse;

public interface BaseGroup
{
	public BusList getBuses() throws PsseModelException;
	public GeneratorList getGenerators() throws PsseModelException;
	public LoadList getLoads() throws PsseModelException;
	public LineList getLines() throws PsseModelException;
	public TransformerList getTransformers() throws PsseModelException;
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException;

	public GroupList getGroup(String type) throws PsseModelException;
	public String[] getGroupTypes();
	
	public IslandList getIslands() throws PsseModelException;
	
	
	public OutBusList getOutBusses() throws PsseModelException;
	
}
