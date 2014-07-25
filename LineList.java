package com.powerdata.openpa;

import com.powerdata.openpa.impl.LineListI;

public interface LineList extends ACBranchListIfc<Line>
{

	static LineList	Empty	= new LineListI();

	float getFromBchg(int ndx) throws PAModelException;

	void setFromBchg(int ndx, float b) throws PAModelException;
	
	float[] getFromBchg() throws PAModelException;
	
	void setFromBchg(float[] b) throws PAModelException;

	float getToBchg(int ndx) throws PAModelException;

	void setToBchg(int ndx, float b) throws PAModelException;
	
	float[] getToBchg() throws PAModelException;
	
	void setToBchg(float[] b) throws PAModelException;

}
