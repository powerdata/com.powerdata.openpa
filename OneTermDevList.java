package com.powerdata.openpa;

public interface OneTermDevList<T extends OneTermDev> extends BaseList<T> 
{
	Bus getBus(int ndx);

	void setBus(int ndx, Bus b);
	
	Bus[] getBus();
	
	void setBus(Bus[] b);
	
	float getP(int ndx);
	
	void setP(int ndx, float p);

	float[] getP();
	
	void setP(float[] p);
	
	float getQ(int ndx);

	void setQ(int ndx, float q);
	
	float[] getQ();
	
	void setQ(float[] q);

	boolean isInSvc(int ndx);

	void setInSvc(int ndx, boolean state);
	
	boolean[] isInSvc();
	
	void setInSvc(boolean[] state);

}
