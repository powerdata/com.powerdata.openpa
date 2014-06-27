package com.powerdata.openpa;

public class ShuntReacList extends ShuntList<ShuntReactor>
{

	public static final ShuntReacList	Empty	= new ShuntReacList();

	protected ShuntReacList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected ShuntReacList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected ShuntReacList() {super();}

	@Override
	public ShuntReactor get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	//TODO:  add "switchable", "order", and insvc

	@Override
	protected ShuntReactor[] newarray(int size)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
