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

	float getFromP(int ndx) throws PAModelException;

	void setFromP(int ndx, float mw) throws PAModelException;
	
	float[] getFromP() throws PAModelException;
	
	void setFromP(float[] mw) throws PAModelException;

	float getFromQ(int ndx) throws PAModelException;

	void setFromQ(int ndx, float mvar) throws PAModelException;
	
	float[] getFromQ() throws PAModelException;
	
	void setFromQ(float[] mvar) throws PAModelException;

	float getToP(int ndx) throws PAModelException;

	void setToP(int ndx, float mw) throws PAModelException;
	
	float[] getToP() throws PAModelException;
	
	void setToP(float[] mw) throws PAModelException;

	float getToQ(int ndx) throws PAModelException;

	void setToQ(int ndx, float mvar) throws PAModelException;
	
	float[] getToQ() throws PAModelException;
	
	void setToQ(float[] mvar) throws PAModelException;

}
