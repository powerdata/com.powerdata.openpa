package com.powerdata.openpa;


public class OwnerList extends EquipLists<Owner>
{
	public static final OwnerList Empty = new OwnerList();

	public OwnerList() {super();}
	
	public OwnerList(PALists model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public OwnerList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexes(busref), ngrp);
	}

	@Override
	public Owner get(int index)
	{
		return new Owner(this, index);
	}

	@Override
	protected Owner[] newarray(int size)
	{
		return new Owner[size];
	}
}
