package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.ShuntCapList;
import com.powerdata.openpa.ShuntCapacitor;

public class ShuntCapSubList extends FixedShuntSubList<ShuntCapacitor> implements ShuntCapList
{

	public ShuntCapSubList(ShuntCapList src, int[] ndx)
	{
		super(src, ndx);
	}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.ShuntCap;
	}
	
}
