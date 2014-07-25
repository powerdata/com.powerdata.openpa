package com.powerdata.openpa;

public interface TwoTermDevListIfc<T extends TwoTermDev> extends BaseList<T>
{
	Bus getFromBus(int ndx) throws PAModelException;
	
	void setFromBus(int ndx, Bus b) throws PAModelException;
	
	Bus[] getFromBus() throws PAModelException;
	
	void setFromBus(Bus[] b) throws PAModelException;

	Bus getToBus(int ndx) throws PAModelException;
	
	void setToBus(int ndx, Bus b) throws PAModelException;
	
	Bus[] getToBus() throws PAModelException;
	
	void setToBus(Bus[] b) throws PAModelException;

	boolean isOutOfSvc(int ndx) throws PAModelException;

	void setOutOfSvc(int ndx, boolean state) throws PAModelException;
	
	boolean[] isOutOfSvc() throws PAModelException;
	
	void setOutOfSvc(boolean[] state) throws PAModelException;

}
