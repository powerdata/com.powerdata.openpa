package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
