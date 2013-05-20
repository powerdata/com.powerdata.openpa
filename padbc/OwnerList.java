package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class OwnerList<T extends Owner> extends BaseList<T>
{
	public abstract String getOwnerName(int index);
}
