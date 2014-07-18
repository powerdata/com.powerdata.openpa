package com.powerdata.openpa;

public interface ACBranchListIfc<T extends ACBranch> extends TwoTermDevListIfc<T>
{

	float getR(int ndx);

	void setR(int ndx, float r);
	
	float[] getR();
	
	void setR(float[] r);

	float getX(int ndx);
	
	void setX(int ndx, float x);

	float[] getX();
	
	void setX(float[] x);
}
