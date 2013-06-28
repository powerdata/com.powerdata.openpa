package com.powerdata.openpa.psse;

public interface BaseOutputGroup
{
	public BusOutList getBusses() throws PsseModelException;
	public SwitchOutList getSwitches() throws PsseModelException;
	/* TODO:  Add remaining output lists */
}
