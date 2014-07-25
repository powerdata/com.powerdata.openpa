package com.powerdata.openpa.impl;

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
