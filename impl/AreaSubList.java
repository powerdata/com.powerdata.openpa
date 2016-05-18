package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.Area;
import com.powerdata.openpa.AreaList;

public class AreaSubList extends GroupSubList<Area> implements AreaList
{
	AreaList _src;
	
	public AreaSubList(AreaList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Area get(int index)
	{
		return new Area(_src, index);
	}
}
