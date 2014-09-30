package com.powerdata.openpa;

import com.powerdata.openpa.impl.IslandListI;


public interface IslandList extends GroupListIfc<Island>
{
	//static BusList	Empty	= new BusListI();
	static IslandList Empty = new IslandListI();

	boolean isEnergized(int ndx) throws PAModelException;
	
	boolean[] isEnergized() throws PAModelException;

	float getFreq(int ndx) throws PAModelException;

	void setFreq(int ndx, float f) throws PAModelException;
	
	float[] getFreq() throws PAModelException;
	
	void setFreq(float[] f) throws PAModelException;

}
