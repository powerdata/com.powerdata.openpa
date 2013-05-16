package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class PhaseShftWndList extends AbstractList<PhaseShifterWinding> implements BaseList
{
	public abstract int getFromNode(int ndx);
	public abstract int getToNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract float getFromBChg(int ndx);
	public abstract float getToBChg(int ndx);
	public abstract float getPhaseShift(int ndx);
	public abstract void updateActvPower(int ndx, float p);
	public abstract void updateReacPower(int ndx, float q);
	
//	public abstract void updateMW(int ndx, float p);
//	public abstract void updateMVAr(int ndx, float q);
	
	@Override
	public PhaseShifterWinding get(int ndx) {return new PhaseShifterWinding(ndx, this);}
}
