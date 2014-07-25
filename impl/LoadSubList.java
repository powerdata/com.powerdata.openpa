package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Load;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModelException;

public class LoadSubList extends OneTermDevSubList<Load> implements LoadList
{
	LoadList _src;
	
	public LoadSubList(LoadList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}

	@Override
	public float getMaxP(int ndx) throws PAModelException
	{
		return _src.getMaxP(_ndx[ndx]);
	}

	@Override
	public void setMaxP(int ndx, float mw) throws PAModelException
	{
		_src.setMaxP(_ndx[ndx], mw);
	}

	@Override
	public float[] getMaxP() throws PAModelException
	{
		return mapFloat(_src.getMaxP());
	}

	@Override
	public void setMaxP(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxP(_ndx[i], mw[i]);
	}

	@Override
	public float getMaxQ(int ndx) throws PAModelException
	{
		return _src.getMaxQ(_ndx[ndx]);
	}

	@Override
	public void setMaxQ(int ndx, float mvar) throws PAModelException
	{
		_src.setMaxQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getMaxQ() throws PAModelException
	{
		return mapFloat(_src.getMaxQ());
	}

	@Override
	public void setMaxQ(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxQ(_ndx[i], mvar[i]);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Load;
	}
}
