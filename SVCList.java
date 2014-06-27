package com.powerdata.openpa;

public class SVCList extends ShuntList<SVC>
{

	public static final SVCList	Empty	= new SVCList();

	public SVCList(PALists model, int[] keys, int[] buskeys)
	{
		super(model, keys, buskeys);
		// TODO Auto-generated constructor stub
	}
	public SVCList(PALists model, int size, int[] buskeys)
	{
		super(model, size, buskeys);
		// TODO Auto-generated constructor stub
	}

	protected SVCList() {super();}

	@Override
	public SVC get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected SVC[] newarray(int size)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
