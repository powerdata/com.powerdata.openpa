package com.powerdata.openpa;

public interface LoadList extends OneTermDevList<Load>
{

	static final LoadList Empty = new LoadListI();

	float getMaxP(int ndx);

	void setMaxP(int ndx, float mw);

	float[] getMaxP();
	
	void setMaxP(float[] mw);
	
	float getMaxQ(int ndx);

	void setMaxQ(int ndx, float mvar);
	
	float[] getMaxQ();
	
	void setMaxQ(float[] mvar);

}
