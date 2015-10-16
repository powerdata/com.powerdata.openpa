package com.powerdata.openpa;

public interface OneTermDevListIfc<T extends OneTermDev> extends InServiceList<T>,OneTermBaseList<T>
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

	default public int[] getBusIndexes() throws PAModelException
	{
		int n = size();
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getBus(i).getIndex();
		return rv;
	}
}
