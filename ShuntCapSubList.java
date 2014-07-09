package com.powerdata.openpa;

public class ShuntCapSubList extends ShuntSubList<ShuntCapacitor> implements ShuntCapList
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
}
