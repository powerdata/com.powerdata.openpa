package com.powerdata.openpa;

import com.powerdata.openpa.impl.LoadListI;

public interface LoadList extends OneTermDevListIfc<Load>
{

	static final LoadList Empty = new LoadListI();

	float getMaxP(int ndx) throws PAModelException;

	void setMaxP(int ndx, float mw) throws PAModelException;

	float[] getMaxP() throws PAModelException;
	
	void setMaxP(float[] mw) throws PAModelException;
	
	float getMaxQ(int ndx) throws PAModelException;

	void setMaxQ(int ndx, float mvar) throws PAModelException;
	
	float[] getMaxQ() throws PAModelException;
	
	void setMaxQ(float[] mvar) throws PAModelException;

}
