package com.powerdata.openpa;

public abstract class ShuntList<T extends Shunt> extends OneTermDevList<T> 
{
	public ShuntList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
		// TODO Auto-generated constructor stub
	}
	public ShuntList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected ShuntList() {super();}
}
