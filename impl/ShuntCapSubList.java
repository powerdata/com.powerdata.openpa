package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.ShuntCapList;
import com.powerdata.openpa.ShuntCapacitor;

public class ShuntCapSubList extends FixedShuntSubList<ShuntCapacitor> implements ShuntCapList
{

	public ShuntCapSubList(ShuntCapList src, int[] ndx)
	{
		super(src, ndx);
	}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.ShuntCap;
	}
	
}
