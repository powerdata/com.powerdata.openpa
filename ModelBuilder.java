package com.powerdata.openpa;

public interface ModelBuilder
{
	public boolean hasErrors();
	public String[] getErrors();
	PAModel load() throws PAModelException;
}
