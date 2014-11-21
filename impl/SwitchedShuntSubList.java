package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SwitchedShunt;
import com.powerdata.openpa.SwitchedShuntList;

public class SwitchedShuntSubList extends OneTermDevSubList<SwitchedShunt> implements SwitchedShuntList
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

}
