package com.powerdata.openpa;

public class SwitchedShuntList extends ShuntList<SwitchedShunt>
{

	public static final SwitchedShuntList	Empty	= new SwitchedShuntList();

	public SwitchedShuntList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
		// TODO Auto-generated constructor stub
	}

	public SwitchedShuntList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected SwitchedShuntList() {super();}

	@Override
	public SwitchedShunt get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
