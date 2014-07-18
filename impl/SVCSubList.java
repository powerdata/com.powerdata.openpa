package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVCList;

public class SVCSubList extends ShuntSubList<SVC> implements SVCList
{
	SVCList _src;
	
	public SVCSubList(SVCList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public SVC get(int index)
	{
		return new SVC(this, index);
	}

	@Override
	public float getMinB(int ndx)
	{
		return _src.getMinB(_ndx[ndx]);
	}

	@Override
	public void setMinB(int ndx, float b)
	{
		_src.setMinB(_ndx[ndx], b);
	}

	@Override
	public float[] getMinB()
	{
		return mapFloat(_src.getMinB());
	}

	@Override
	public void setMinB(float[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setMinB(_ndx[i], b[i]);
	}

	@Override
	public float getMaxB(int ndx)
	{
		return _src.getMaxB(_ndx[ndx]);
	}

	@Override
	public void setMaxB(int ndx, float b)
	{
		_src.setMaxB(_ndx[ndx], b);
	}

	@Override
	public float[] getMaxB()
	{
		return mapFloat(_src.getMaxB());
	}

	@Override
	public void setMaxB(float[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxB(_ndx[i], b[i]);
	}

	@Override
	public boolean isRegKV(int ndx)
	{
		return _src.isRegKV(_ndx[ndx]);
	}

	@Override
	public void setRegKV(int ndx, boolean reg)
	{
		_src.setRegKV(_ndx[ndx], reg);
	}

	@Override
	public boolean[] isRegKV()
	{
		return mapBool(_src.isRegKV());
	}

	@Override
	public void setRegKV(boolean[] reg)
	{
		for(int i=0; i < _size; ++i)
			_src.setRegKV(_ndx[i], reg[i]);
	}

	@Override
	public float getVS(int ndx)
	{
		return _src.getVS(_ndx[ndx]);
	}

	@Override
	public void setVS(int ndx, float kv)
	{
		_src.setVS(_ndx[ndx], kv);
	}

	@Override
	public float[] getVS()
	{
		return mapFloat(_src.getVS());
	}

	@Override
	public void setVS(float[] kv)
	{
		for(int i=0; i < _size; ++i)
			_src.setVS(_ndx[i], kv[i]);
	}

	@Override
	public Bus getRegBus(int ndx)
	{
		return _src.getRegBus(_ndx[ndx]);
	}

	@Override
	public void setRegBus(int ndx, Bus b)
	{
		_src.setRegBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getRegBus()
	{
		return mapObject(_src.getRegBus());
	}

	@Override
	public void setRegBus(Bus[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setRegBus(_ndx[i], b[i]);
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.SVC;
	}

}
