package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class AreaList<T extends Area> extends BaseList<T>
{
	public abstract String getAreaName(int index);
}
