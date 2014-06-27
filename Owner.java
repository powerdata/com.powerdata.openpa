package com.powerdata.openpa;

public class Owner extends AbstractGroupObject
{
	protected OwnerList _list;
	
	public Owner(OwnerList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
}
