package com.powerdata.openpa;

public interface SVCList extends ShuntList<SVC>
{

	static final SVCList Empty = new SVCListImpl();

	float getMinB(int ndx);

	void setMinB(int ndx, float b);
	
	float[] getMinB();
	
	void setMinB(float[] b);

	float getMaxB(int ndx);

	void setMaxB(int ndx, float b);
	
	float[] getMaxB();
	
	void setMaxB(float[] b);

	boolean isRegKV(int ndx);

	void setRegKV(int ndx, boolean reg);
	
	boolean[] isRegKV();
	
	void setRegKV(boolean[] reg);

	float getVS(int ndx);

	void setVS(int ndx, float kv);
	
	float[] getVS();
	
	void setVS(float[] kv);

	Bus getRegBus(int ndx);

	void setRegBus(int ndx, Bus b);
	
	Bus[] getRegBus();
	
	void setRegBus(Bus[] b);

}
