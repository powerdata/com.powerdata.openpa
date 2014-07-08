package com.powerdata.openpa;

public class ShuntReacListImpl extends ShuntListImpl<ShuntReactor> implements ShuntReacList
{

	public static final ShuntReacList	Empty	= new ShuntReacListImpl();

	protected ShuntReacListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
		// TODO Auto-generated constructor stub
	}

	protected ShuntReacListImpl(PAModel model, int size)
	{
		super(model, size);
		// TODO Auto-generated constructor stub
	}

	protected ShuntReacListImpl() {super();}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

}
