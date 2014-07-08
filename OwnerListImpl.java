package com.powerdata.openpa;


public class OwnerListImpl extends GroupListI<Owner> implements OwnerList
{
	public static final OwnerList Empty = new OwnerListImpl();

	public OwnerListImpl() {super();}
	
	public OwnerListImpl(PAModel model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public OwnerListImpl(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexesFromKeys(busref), ngrp);
	}

	@Override
	public Owner get(int index)
	{
		return new Owner(this, index);
	}
}
