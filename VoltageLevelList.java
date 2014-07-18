package com.powerdata.openpa;


public interface VoltageLevelList extends GroupListIfc<VoltageLevel>
{

	float getBaseKV(int ndx);

	void setBaseKV(int ndx, float k);
	
	float[] getBaseKV();
	
	void setBaseKV(float[] kv);

}
