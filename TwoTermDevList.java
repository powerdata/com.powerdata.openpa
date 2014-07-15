package com.powerdata.openpa;

public interface TwoTermDevList<T extends TwoTermDev> extends BaseList<T>
{
	Bus getFromBus(int ndx);
	
	void setFromBus(int ndx, Bus b);
	
	Bus[] getFromBus();
	
	void setFromBus(Bus[] b);

	Bus getToBus(int ndx);
	
	void setToBus(int ndx, Bus b);
	
	Bus[] getToBus();
	
	void setToBus(Bus[] b);

	boolean isInSvc(int ndx);

	void setInSvc(int ndx, boolean state);
	
	boolean[] isInSvc();
	
	void setInSvc(boolean[] state);

}
