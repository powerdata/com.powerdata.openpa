package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class SwitchedShuntList<T extends SwitchedShunt> extends BaseList<T>
{
	public abstract float getNominalB(int index);
	public abstract void updateReacPwr(int index, float b);
}
