package com.powerdata.openpa.impl;

import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.PAModelException;

public class IslandSubList extends GroupSubList<Island> implements IslandList
{
	IslandList _src;
	public IslandSubList(IslandList src, int[] ndx)
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
	public Island get(int index)
	{
		return new Island(this, index);
	}
}
