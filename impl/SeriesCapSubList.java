package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesCapList;

public class SeriesCapSubList extends ACBranchSubList<SeriesCap> implements SeriesCapList
{

	public SeriesCapSubList(SeriesCapList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SeriesCap get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.SeriesCap;
	}
}
