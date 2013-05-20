package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class StationList<T extends Station> extends BaseList<T>
{
	public abstract String getStationName(int index);
}
