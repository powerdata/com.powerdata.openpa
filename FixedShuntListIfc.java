package com.powerdata.openpa;

public interface FixedShuntListIfc<T extends FixedShunt> extends OneTermDevListIfc<T>
{

	float getB(int ndx) throws PAModelException;

	void setB(int ndx, float b) throws PAModelException;
	
	float[] getB() throws PAModelException;
	
	void setB(float[] b) throws PAModelException;

}
