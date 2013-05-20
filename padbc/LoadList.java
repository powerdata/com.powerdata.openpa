package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class LoadList<T extends Load> extends BaseList<T>
{
	public abstract float getReacPwr(int ndx);
	public abstract float getActvPwr(int ndx);
	public abstract void updateReacPwr(int ndx, float p);
	public abstract void updateActvPwr(int ndx, float q);
}
