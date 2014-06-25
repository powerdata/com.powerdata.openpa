package com.powerdata.openpa;

public class TwoTermDCLineList extends TwoTermDevList<TwoTermDCLine>
{

	public static final TwoTermDCLineList	Empty	= new TwoTermDCLineList();

	protected TwoTermDCLineList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected TwoTermDCLineList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	protected TwoTermDCLineList() {super();}

	@Override
	public TwoTermDCLine get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
