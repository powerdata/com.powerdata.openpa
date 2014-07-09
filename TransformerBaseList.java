package com.powerdata.openpa;

public interface TransformerBaseList<T extends TransformerBase> extends ACBranchList<T>
{

	float getGmag(int ndx);

	void setGmag(int ndx, float g);
	
	float[] getGmag();
	
	void setGmag(float[] g);

	float getBmag(int ndx);

	void setBmag(int ndx, float b);
	
	float[] getBmag();
	
	void setBmag(float[] b);

	float getShift(int ndx);

	void setShift(int ndx, float sdeg);
	
	float[] getShift();
	
	void setShift(float[] sdeg);

}
