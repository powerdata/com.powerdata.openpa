package com.powerdata.openpa;

public interface ACBranchListIfc<T extends ACBranch> extends TwoTermDevListIfc<T>
{

	float getR(int ndx) throws PAModelException;

	void setR(int ndx, float r) throws PAModelException;
	
	float[] getR() throws PAModelException;
	
	void setR(float[] r) throws PAModelException;

	float getX(int ndx) throws PAModelException;
	
	void setX(int ndx, float x) throws PAModelException;

	float[] getX() throws PAModelException;
	
	void setX(float[] x) throws PAModelException;
}
