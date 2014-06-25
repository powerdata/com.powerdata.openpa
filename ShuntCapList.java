package com.powerdata.openpa;

public class ShuntCapList extends ShuntList<ShuntCapacitor>
{

	public static final ShuntCapList	Empty	= new ShuntCapList();

	protected ShuntCapList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
		// TODO Auto-generated constructor stub
	}
	protected ShuntCapList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected ShuntCapList() {super();}

	@Override
	public ShuntCapacitor get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	//TODO:  add "switchable", "order", and insvc

}
