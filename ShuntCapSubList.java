package com.powerdata.openpa;

public class ShuntCapSubList extends ShuntSubList<ShuntCapacitor> implements ShuntCapList
{

	public ShuntCapSubList(ShuntList<ShuntCapacitor> src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}
}
