package com.powerdata.openpa;

public class ShuntCapListImpl extends ShuntListImpl<ShuntCapacitor> implements ShuntCapList
{

	public static final ShuntCapList	Empty	= new ShuntCapListImpl();

	public ShuntCapListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public ShuntCapListImpl(PAModel model, int size)
	{
		super(model, size);
	}
	public ShuntCapListImpl() {super();}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}

}
