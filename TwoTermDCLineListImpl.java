package com.powerdata.openpa;

public class TwoTermDCLineListImpl extends TwoTermDevListI<TwoTermDCLine> implements TwoTermDCLineList
{

	public static final TwoTermDCLineList	Empty	= new TwoTermDCLineListImpl();

	public TwoTermDCLineListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public TwoTermDCLineListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public TwoTermDCLineListImpl() {super();}

	@Override
	public TwoTermDCLine get(int index)
	{
		return new TwoTermDCLine(this, index);
	}

}
