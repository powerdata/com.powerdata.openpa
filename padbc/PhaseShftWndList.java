package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class PhaseShftWndList<T extends PhaseShifterWinding> extends BaseList<T>
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
}
