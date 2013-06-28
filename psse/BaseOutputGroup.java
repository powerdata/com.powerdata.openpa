package com.powerdata.openpa.psse;

public interface BaseOutputGroup
{
	public BusOutList getBusses() throws PsseModelException;
	public abstract LineOutList getLines();
	public abstract TransformerOutList getTransformers();
}
