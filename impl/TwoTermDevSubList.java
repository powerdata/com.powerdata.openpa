package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public abstract class TwoTermDevSubList<T extends TwoTermDev> extends SubList<T> implements TwoTermDevListIfc<T>
{
	TwoTermDevListIfc<T> _src;

	
	public TwoTermDevSubList(TwoTermDevListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Bus getFromBus(int ndx)
	{
		return _src.getFromBus(_ndx[ndx]);
	}

	@Override
	public void setFromBus(int ndx, Bus b)
	{
		_src.setFromBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getFromBus()
	{
		return mapObject(_src.getFromBus());
	}

	@Override
	public void setFromBus(Bus[] b)
	{
		for (int i = 0; i < _size; ++i)
			_src.setFromBus(_ndx[i], b[i]);
	}

	@Override
	public Bus getToBus(int ndx)
	{
		return _src.getToBus(_ndx[ndx]);
	}

	@Override
	public void setToBus(int ndx, Bus b)
	{
		_src.setToBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getToBus()
	{
		return mapObject(_src.getToBus());
	}

	@Override
	public void setToBus(Bus[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setToBus(_ndx[i], b[i]);
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
