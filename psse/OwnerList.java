package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class OwnerList<T extends Owner> extends BaseList<T>
{
	public abstract int getI(int ndx);
	public abstract String getOWNAME(int ndx);
}
