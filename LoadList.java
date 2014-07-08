package com.powerdata.openpa;

public interface LoadList extends OneTermDevList<Load>
{

	static final LoadList Empty = new LoadListImpl();

	float getPL(int ndx);
	
	void setPL(int ndx, float pl);
	
	float[] getPL();
	
	void setPL(float[] pl);
	
}
