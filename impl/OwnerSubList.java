package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.OwnerList;

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

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Owner;
	}

}
