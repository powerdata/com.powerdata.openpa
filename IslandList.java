package com.powerdata.openpa;


public interface IslandList extends GroupListIfc<Island>
{

	boolean isEnergized(int ndx);
	
	boolean[] isEnergized();

	float getFreq(int ndx);

	void setFreq(int ndx, float f);
	
	float[] getFreq();
	
	void setFreq(float[] f);

	Bus getFreqSrc(int ndx);

	void setFreqSrc(int ndx, Bus fsrc);
	
	Bus[] getFreqSrc();
	
	void setFreqSrc(Bus[] fsrc);
}
