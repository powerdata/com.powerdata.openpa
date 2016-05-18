package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.VoltageLevelList;

public class VoltageLevelSubList extends GroupSubList<VoltageLevel> implements VoltageLevelList
{
	VoltageLevelList _src;
	
	public VoltageLevelSubList(VoltageLevelList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getBaseKV(int ndx) throws PAModelException
	{
		return _src.getBaseKV(_ndx[ndx]);
	}

	@Override
	public void setBaseKV(int ndx, float k) throws PAModelException
	{
		_src.setBaseKV(_ndx[ndx], k);
	}

	@Override
	public float[] getBaseKV() throws PAModelException
	{
		return mapFloat(_src.getBaseKV());
	}

	@Override
	public void setBaseKV(float[] kv) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setBaseKV(_ndx[i], kv[i]);
	}

	@Override
	public VoltageLevel get(int index)
	{
		return new VoltageLevel(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.VoltageLevel;
	}

	
}
