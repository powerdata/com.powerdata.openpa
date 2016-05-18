package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.Line;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.ListMetaType;

public class LineSubList extends ACBranchSubListBase<Line> implements LineList
{
	LineList _src;
	
	public LineSubList(LineList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}


	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Line;
	}
	
}