package com.powerdata.openpa;

public class SVCListImpl extends ShuntListImpl<SVC> implements SVCList
{

	public static final SVCList	Empty	= new SVCListImpl();

	public SVCListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
		// TODO Auto-generated constructor stub
	}
	public SVCListImpl(PAModel model, int size)
	{
		super(model, size);
		// TODO Auto-generated constructor stub
	}

	protected SVCListImpl() {super();}

	@Override
	public SVC get(int index)
	{
		return new SVC(this, index);
	}

}
