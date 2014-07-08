package com.powerdata.openpa;

public interface PhaseShifterList extends TransformerBaseList<PhaseShifter>
{

	static final PhaseShifterList Empty = new PhaseShifterListImpl();

	float getShift(int ndx);

	void setShift(int ndx, float sdeg);
	
	float[] getShift();
	
	void setShift(float[] sdeg);
}
