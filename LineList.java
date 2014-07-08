package com.powerdata.openpa;

public interface LineList extends ACBranchList<Line>
{

	float getFromBchg(int ndx);

	void setFromBchg(int ndx, float b);
	
	float[] getFromBchg();
	
	void setFromBchg(float[] b);

	float getToBchg(int ndx);

	void setToBchg(int ndx, float b);
	
	float[] getToBchg();
	
	void setToBchg(float[] b);

}
