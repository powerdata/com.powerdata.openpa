package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public abstract class TwoTermDevSubList<T extends TwoTermDev> extends InServiceSubList<T> implements TwoTermDevListIfc<T>
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
	public float getFromP(int ndx) throws PAModelException
	{
		return _src.getFromP(_ndx[ndx]);
	}

	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		_src.setFromP(_ndx[ndx], mw);
	}

	@Override
	public float[] getFromP() throws PAModelException
	{
		return mapFloat(_src.getFromP());
	}

	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromP(_ndx[i], mw[i]);
	}

	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		return _src.getFromQ(_ndx[ndx]);
	}

	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		_src.setFromQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getFromQ() throws PAModelException
	{
		return mapFloat(_src.getFromQ());
	}

	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromQ(_ndx[i], mvar[i]);
	}

	@Override
	public float getToP(int ndx) throws PAModelException
	{
		return _src.getToP(_ndx[ndx]);
	}

	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		_src.setToP(_ndx[ndx], mw);
	}

	@Override
	public float[] getToP() throws PAModelException
	{
		return mapFloat(_src.getToP());
	}

	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToP(_ndx[i], mw[i]);
	}

	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		return _src.getToQ(_ndx[ndx]);
	}

	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		_src.setToQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getToQ() throws PAModelException
	{
		return mapFloat(_src.getToQ());
	}

	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToQ(_ndx[i], mvar[i]);
	}


}
