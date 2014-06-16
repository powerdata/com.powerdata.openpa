package com.powerdata.openpa;

public class LoadList extends OneTermDevList<Load>
{
	public static final LoadList	Empty	= new LoadList();

	protected LoadList(PALists model, int[] keys)
	{
		super(model, keys);
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

