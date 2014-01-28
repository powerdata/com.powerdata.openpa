package com.powerdata.openpa.psse;

/**
 * 
 * @author chris
 *
 */
public interface PsseLists
{
	public BusList getBuses() throws PsseModelException;
	public GenList getGenerators() throws PsseModelException;
	public LoadList getLoads() throws PsseModelException;
	public LineList getLines() throws PsseModelException;
	public TransformerList getTransformers() throws PsseModelException;
	public PhaseShifterList getPhaseShifters() throws PsseModelException;
	public SwitchList getSwitches() throws PsseModelException;
	public ShuntList getShunts() throws PsseModelException;
	public SvcList getSvcs() throws PsseModelException;
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException;
	public TwoTermDCLineList getTwoTermDCLines() throws PsseModelException;


}
