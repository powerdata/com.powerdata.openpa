package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.TwoTermDCLine;
import com.powerdata.openpa.TwoTermDCLineList;

public class TwoTermDCLineSubList extends TwoTermDevSubList<TwoTermDCLine> implements TwoTermDCLineList
{

	public TwoTermDCLineSubList(TwoTermDCLineList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TwoTermDCLine get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.TwoTermDCLine;
	}
}
