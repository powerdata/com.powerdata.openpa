package com.powerdata.openpa;

public interface ShuntList<T extends Shunt> extends OneTermDevList<T>
{

	float getBS(int ndx);

	void setBS(int ndx, float b);
	
	float[] getBS();
	
	void setBS(float[] b);

}
