package com.powerdata.openpa;

public class ShuntReacListI extends ShuntListImpl<ShuntReactor> implements ShuntReacList
{

	public ShuntReacListI(PAModel model, int[] keys)
	{
		super(model, keys);
	}

	public ShuntReacListI(PAModel model, int size)
	{
		super(model, size);
	}

	public ShuntReacListI() {super();}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

}
