package com.powerdata.openpa;


public interface IslandList extends GroupListIfc<Island>
{

	boolean isEnergized(int ndx) throws PAModelException;
	
	boolean[] isEnergized() throws PAModelException;

	float getFreq(int ndx) throws PAModelException;

	void setFreq(int ndx, float f) throws PAModelException;
	
	float[] getFreq() throws PAModelException;
	
	void setFreq(float[] f) throws PAModelException;

}
