package com.powerdata.openpa.psse;

public interface Group
{
	public String getGroupName() throws PsseModelException;
	public BusList getBuses() throws PsseModelException;
	public GeneratorList getGenerators() throws PsseModelException;
	public LineList getNontransformerBranches() throws PsseModelException;
	public TransformerList getTransformers() throws PsseModelException;
	public OwnerList getOwners() throws PsseModelException;
	public AreaList getAreas() throws PsseModelException;
	public ZoneList getZones() throws PsseModelException;
	public ImpCorrTblList getImpCorrTables() throws PsseModelException;
	public LoadList getLoads() throws PsseModelException;
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException;
}
