package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.SeriesReacList;

public class SeriesReacSubList extends ACBranchSubList<SeriesReac> implements SeriesReacList
{

	public SeriesReacSubList(SeriesReacList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SeriesReac get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.SeriesReac;
	}
}
