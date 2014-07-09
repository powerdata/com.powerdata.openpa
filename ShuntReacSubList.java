package com.powerdata.openpa;

public class ShuntReacSubList extends ShuntSubList<ShuntReactor> implements ShuntReacList
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
}
