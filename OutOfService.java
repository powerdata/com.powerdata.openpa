package com.powerdata.openpa;

public interface OutOfService
{
	boolean isOutOfSvc(int ndx) throws PAModelException;
	boolean[] isOutOfSvc() throws PAModelException;
}
