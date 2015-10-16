package com.powerdata.openpa;

public interface InServiceList<T extends InService> extends BaseList<T>
{
	boolean isInService(int ndx) throws PAModelException;
	boolean[] isInService() throws PAModelException;
	void setInService(int ndx, boolean s) throws PAModelException;
	void setInService(boolean[] s) throws PAModelException;
}
