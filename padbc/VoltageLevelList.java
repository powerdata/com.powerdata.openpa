package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class VoltageLevelList<T extends VoltageLevel> extends BaseList<T>
{
	public abstract float getNominalKV(int ndx);
}
