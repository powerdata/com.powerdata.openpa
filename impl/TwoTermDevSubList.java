package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.PAModelException;
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
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _src.getFromBus(_ndx[ndx]);
	}

	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		_src.setFromBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return mapObject(_src.getFromBus());
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		for (int i = 0; i < _size; ++i)
			_src.setFromBus(_ndx[i], b[i]);
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _src.getToBus(_ndx[ndx]);
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		_src.setToBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return mapObject(_src.getToBus());
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToBus(_ndx[i], b[i]);
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		return _src.isOutOfSvc(_ndx[ndx]);
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		_src.setOutOfSvc(_ndx[ndx], state);
	}

	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return mapBool(_src.isOutOfSvc());
	}

	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOutOfSvc(_ndx[i], state[i]);
	}

}
