package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.PAModelException;

public class ElectricalIslandSubList extends GroupSubList<ElectricalIsland> implements ElectricalIslandList
{
	ElectricalIslandList _src;
	public ElectricalIslandSubList(ElectricalIslandList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public boolean isEnergized(int ndx) throws PAModelException
	{
		return _src.isEnergized(_ndx[ndx]);
	}

	@Override
	public boolean[] isEnergized() throws PAModelException
	{
		return mapBool(_src.isEnergized());
	}

	@Override
	public float getFreq(int ndx) throws PAModelException
	{
		return _src.getFreq(_ndx[ndx]);
	}

	@Override
	public void setFreq(int ndx, float f) throws PAModelException
	{
		_src.setFreq(_ndx[ndx], f);
	}

	@Override
	public float[] getFreq() throws PAModelException
	{
		return mapFloat(_src.getFreq());
	}

	@Override
	public void setFreq(float[] f) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFreq(_ndx[i], f[i]);
	}

	@Override
	public ElectricalIsland get(int index)
	{
		return new ElectricalIsland(this, index);
	}
}
