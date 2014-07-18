package com.powerdata.openpa;

import com.powerdata.openpa.impl.LineListI;

public interface LineList extends ACBranchListIfc<Line>
{

	static LineList	Empty	= new LineListI();

	float getFromBchg(int ndx);

	void setFromBchg(int ndx, float b);
	
	float[] getFromBchg();
	
	void setFromBchg(float[] b);

	float getToBchg(int ndx);

	void setToBchg(int ndx, float b);
	
	float[] getToBchg();
	
	void setToBchg(float[] b);

}
