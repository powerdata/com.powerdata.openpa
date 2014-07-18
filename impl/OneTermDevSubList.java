package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;

public abstract class OneTermDevSubList<T extends OneTermDev> extends SubList<T> implements OneTermDevListIfc<T>
{
	OneTermDevListIfc<T> _src;
	
	public OneTermDevSubList(OneTermDevListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Bus getBus(int ndx)
	{
		return _src.getBus(_ndx[ndx]);
	}

	@Override
	public void setBus(int ndx, Bus b)
	{
		_src.setBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getBus()
	{
		return mapObject(_src.getBus());
	}

	@Override
	public void setBus(Bus[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setBus(_ndx[i], b[i]);
	}

	@Override
	public float getP(int ndx)
	{
		return _src.getP(_ndx[ndx]);
	}

	@Override
	public void setP(int ndx, float p)
	{
		_src.setP(_ndx[ndx], p);
	}

	@Override
	public float[] getP()
	{
		return mapFloat(_src.getP());
	}

	@Override
	public void setP(float[] p)
	{
		for(int i=0; i < _size; ++i)
			_src.setP(_ndx[i], p[i]);
	}

	@Override
	public float getQ(int ndx)
	{
		return _src.getQ(_ndx[ndx]);
	}

	@Override
	public void setQ(int ndx, float q)
	{
		_src.setQ(_ndx[ndx], q);
	}

	@Override
	public float[] getQ()
	{
		return mapFloat(_src.getQ());
	}

	@Override
	public void setQ(float[] q)
	{
		for(int i=0; i < _size; ++i)
			_src.setQ(_ndx[i], q[i]);
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		return _src.isInSvc(_ndx[ndx]);
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		_src.setInSvc(_ndx[ndx], state);
	}

	@Override
	public boolean[] isInSvc()
	{
		return mapBool(_src.isInSvc());
	}

	@Override
	public void setInSvc(boolean[] state)
	{
		for(int i=0; i < _size; ++i)
			_src.setInSvc(_ndx[i], state[i]);
	}

}
