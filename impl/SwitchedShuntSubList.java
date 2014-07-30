package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SwitchedShunt;
import com.powerdata.openpa.SwitchedShuntList;

public class SwitchedShuntSubList extends SubList<SwitchedShunt> implements SwitchedShuntList
{
	SwitchedShuntList _src;
	
	public SwitchedShuntSubList(SwitchedShuntList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public SwitchedShunt get(int index)
	{
		return new SwitchedShunt(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SwitchedShunt;
	}

	@Override
	public Bus getRegBus(int ndx) throws PAModelException
	{
		return _src.getRegBus(_ndx[ndx]);
	}
	
}
