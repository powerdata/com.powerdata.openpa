package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesCapList;

public class SeriesCapSubList extends ACBranchSubListBase<SeriesCap> implements SeriesCapList
{
	SeriesCapList _src;
	
	public SeriesCapSubList(SeriesCapList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public SeriesCap get(int index)
	{
		return new SeriesCap(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SeriesCap;
	}
}
