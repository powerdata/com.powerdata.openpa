package com.powerdata.openpa.impl;

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
