package com.powerdata.openpa;

public interface OutOfServiceList<T extends OutOfService> extends BaseList<T>
{
	boolean isOutOfSvc(int ndx) throws PAModelException;
	boolean[] isOutOfSvc() throws PAModelException;
	void setOutOfSvc(int ndx, boolean s) throws PAModelException;
	void setOutOfSvc(boolean[] s) throws PAModelException;
}
