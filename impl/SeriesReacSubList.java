package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.SeriesReacList;

public class SeriesReacSubList extends ACBranchSubListBase<SeriesReac> implements SeriesReacList
{
	SeriesReacList _src;
	
	public SeriesReacSubList(SeriesReacList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public SeriesReac get(int index)
	{
		return new SeriesReac(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SeriesReac;
	}
}
