package com.powerdata.openpa.impl;

import com.powerdata.openpa.Area;
import com.powerdata.openpa.AreaList;

public class AreaSubList extends GroupSubList<Area> implements AreaList
{
	AreaList _src;
	
	public AreaSubList(AreaList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Area get(int index)
	{
		return new Area(_src, index);
	}
}
