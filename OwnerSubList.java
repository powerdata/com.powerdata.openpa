package com.powerdata.openpa;

public class OwnerSubList extends GroupSubList<Owner> implements OwnerList
{

	public OwnerSubList(OwnerList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Owner get(int index)
	{
		return new Owner(this, index);
	}

}
