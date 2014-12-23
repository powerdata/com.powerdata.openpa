package com.powerdata.openpa;

public interface OneTermDevListIfc<T extends OneTermDev> extends OutOfServiceList<T>,OneTermBaseList<T>
{
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

}
