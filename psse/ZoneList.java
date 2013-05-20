package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class ZoneList<T extends Zone> extends BaseList<T>
{
	public abstract int getI(int ndx);
	public abstract String getZONAME(int ndx);
}
