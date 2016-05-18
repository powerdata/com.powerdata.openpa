package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.OwnerList;

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
	public OwnerListI(PAModelI model, int[] busref, int nowner)
	{
		super(model, nowner, _PFld);
		setupMap(busref, nowner);
	}
	public OwnerListI(PAModelI model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}
	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicGroupIndex(getIndexesFromKeys(busref), ngrp);
	}
	@Override
	public Owner get(int index)
	{
		return new Owner(this, index);
	}
}
