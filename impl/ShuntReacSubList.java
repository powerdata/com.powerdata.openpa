package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.ShuntReacList;
import com.powerdata.openpa.ShuntReactor;

public class ShuntReacSubList extends FixedShuntSubList<ShuntReactor> implements ShuntReacList
{

	public ShuntReacSubList(ShuntReacList src, int[] ndx)
	{
		super(src, ndx);
	}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.ShuntReac;
	}
}
