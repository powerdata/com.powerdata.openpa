package com.powerdata.openpa;

public class LoadList extends OneTermDevList<Load>
{
	public static final LoadList	Empty	= new LoadList();

	protected LoadList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
	}

	protected LoadList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
	}

	protected LoadList() {super();}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}

	public float getPL(int _ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

