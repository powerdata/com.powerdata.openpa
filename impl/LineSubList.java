package com.powerdata.openpa.impl;

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