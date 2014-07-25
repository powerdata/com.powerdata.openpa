package com.powerdata.openpa;


public interface VoltageLevelList extends GroupListIfc<VoltageLevel>
{

	float getBaseKV(int ndx) throws PAModelException;

	void setBaseKV(int ndx, float k) throws PAModelException;
	
	float[] getBaseKV() throws PAModelException;
	
	void setBaseKV(float[] kv) throws PAModelException;

}
