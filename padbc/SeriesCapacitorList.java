package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class SeriesCapacitorList<T extends SeriesCapacitor> extends BaseList<T>
{
	public abstract int getFromNode(int ndx);
	public abstract int getToNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract void updateActvPower(int ndx, float p);
	public abstract void updateReacPower(int ndx, float q);
}
