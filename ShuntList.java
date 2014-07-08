package com.powerdata.openpa;

public interface ShuntList<T extends Shunt> extends OneTermDevList<T>
{

	float getB(int ndx);

	void setB(int ndx, float b);
	
	float[] getB();
	
	void setB(float[] b);

}
