package com.powerdata.openpa;

import com.powerdata.openpa.impl.GroupListIfc;

public interface VoltageLevelList extends GroupListIfc<VoltageLevel>
{

	float getBaseKV(int ndx);

	void setBaseKV(int ndx, float k);
	
	float[] getBaseKV();
	
	void setBaseKV(float[] kv);

}
