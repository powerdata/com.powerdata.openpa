package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModelException;

public abstract class OneTermDevSubList<T extends OneTermDev> extends InServiceSubList<T> implements OneTermDevListIfc<T>
{
	OneTermDevListIfc<T> _src;
	
	public OneTermDevSubList(OneTermDevListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Bus getBus(int ndx) throws PAModelException
	{
		return _src.getBus(_ndx[ndx]);
	}

	@Override
	public void setBus(int ndx, Bus b) throws PAModelException
	{
		_src.setBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getBus() throws PAModelException
	{
		return mapObject(_src.getBus());
	}

	@Override
	public void setBus(Bus[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setBus(_ndx[i], b[i]);
	}

	@Override
	public float getP(int ndx) throws PAModelException
	{
		return _src.getP(_ndx[ndx]);
	}

	@Override
	public void setP(int ndx, float p) throws PAModelException
	{
		_src.setP(_ndx[ndx], p);
	}

	@Override
	public float[] getP() throws PAModelException
	{
		return mapFloat(_src.getP());
	}

	@Override
	public void setP(float[] p) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setP(_ndx[i], p[i]);
	}

	@Override
	public float getQ(int ndx) throws PAModelException
	{
		return _src.getQ(_ndx[ndx]);
	}

	@Override
	public void setQ(int ndx, float q) throws PAModelException
	{
		_src.setQ(_ndx[ndx], q);
	}

	@Override
	public float[] getQ() throws PAModelException
	{
		return mapFloat(_src.getQ());
	}

	@Override
	public void setQ(float[] q) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setQ(_ndx[i], q[i]);
	}

}
