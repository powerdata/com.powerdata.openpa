package com.powerdata.openpa;

public interface TransformerList extends TransformerBaseList<Transformer>
{

	static final TransformerList Empty = new TransformerListImpl();
	float getFromTap(int ndx);

	void setFromTap(int ndx, float a);
	
	float[] getFromTap();
	
	void setFromTap(float[] a);

	float getToTap(int ndx);

	void setToTap(int ndx, float a);
	
	float[] getToTap();
	
	void setToTap(float[] a);


}
