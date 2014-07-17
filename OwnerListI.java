package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class OwnerListI extends GroupListI<Owner> implements OwnerList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.OwnerID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.OwnerNAME;
		}
	};
	public OwnerListI()
	{
		super();
	}
	public OwnerListI(PAModel model, int[] busref, int nowner)
	{
		super(model, null, _PFld);
		setupMap(busref, nowner);
	}
	public OwnerListI(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
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
	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Owner;
	}
}
