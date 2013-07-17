package com.powerdata.openpa.psse;

public interface EquipGroup
{
	public GenInList getGenerators() throws PsseModelException;
	public LoadInList getLoads() throws PsseModelException;
	public LineInList getLines() throws PsseModelException;
	public TransformerInList getTransformers() throws PsseModelException;
	public PhaseShifterInList getPhaseShifters() throws PsseModelException;
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException;
	public SwitchList getSwitches() throws PsseModelException;
}
