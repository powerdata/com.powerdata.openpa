package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class TransformerWndList<T extends TransformerWinding> extends BaseList<T>
{
	public abstract int getFromNode(int ndx);
	public abstract int getToNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract float getFromBChg(int ndx);
	public abstract float getToBChg(int ndx);
	public abstract float getBmag(int ndx);
	public abstract float getFromTapRatio(int ndx);
	public abstract float getToTapRatio(int ndx);
	public abstract float getPhaseShift(int ndx);
	public abstract void updateActvPower(int ndx, float p);
	public abstract void updateReacPower(int ndx, float q);
	
}
