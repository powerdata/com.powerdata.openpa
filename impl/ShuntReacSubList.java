package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.ShuntReacList;
import com.powerdata.openpa.ShuntReactor;

public class ShuntReacSubList extends FixedShuntSubList<ShuntReactor> implements ShuntReacList
{

	public ShuntReacSubList(ShuntReacList src, int[] ndx)
	{
		super(src, ndx);
	}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.ShuntReac;
	}
}
