package com.powerdata.openpa;

public interface OneTermDevListIfc<T extends OneTermDev> extends BaseList<T> 
{
	/**
	 * Return the connected Bus object
	 * @param ndx offset within this list
	 * @return Connected Bus object
	 */
	Bus getBus(int ndx) throws PAModelException;

	void setBus(int ndx, Bus b) throws PAModelException;
	
	Bus[] getBus() throws PAModelException;
	
	void setBus(Bus[] b) throws PAModelException;
	
	float getP(int ndx) throws PAModelException;
	
	void setP(int ndx, float p) throws PAModelException;

	float[] getP() throws PAModelException;
	
	void setP(float[] p) throws PAModelException;
	
	float getQ(int ndx) throws PAModelException;

	void setQ(int ndx, float q) throws PAModelException;
	
	float[] getQ() throws PAModelException;
	
	void setQ(float[] q) throws PAModelException;

	boolean isOutOfSvc(int ndx) throws PAModelException;

	void setOutOfSvc(int ndx, boolean state) throws PAModelException;
	
	boolean[] isOutOfSvc() throws PAModelException;
	
	void setOutOfSvc(boolean[] state) throws PAModelException;

}
