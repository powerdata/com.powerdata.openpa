package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;

public class GenSubList extends OneTermDevSubList<Gen> implements GenList
{
	GenList _src;
	
	public GenSubList(GenList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Type getType(int ndx)
	{
		return _src.getType(_ndx[ndx]);
	}

	@Override
	public void setType(int ndx, Type t)
	{
		_src.setType(_ndx[ndx], t);
	}

	@Override
	public Type[] getType()
	{
		return mapObject(_src.getType());
	}

	@Override
	public void setType(Type[] t)
	{
		for(int i=0; i < _size; ++i)
			_src.setType(_ndx[i], t[i]);
	}

	@Override
	public Mode getMode(int ndx)
	{
		return _src.getMode(_ndx[ndx]);
	}

	@Override
	public void setMode(int ndx, Mode m)
	{
		_src.setMode(_ndx[ndx], m);
	}

	@Override
	public Mode[] getMode()
	{
		return mapObject(_src.getMode());
	}

	@Override
	public void setMode(Mode[] m)
	{
		for(int i=0; i < _size; ++i)
			_src.setMode(_ndx[i], m[i]);
	}

	@Override
	public Gen get(int index)
	{
		return new Gen(this, index);
	}

	@Override
	public float getOpMinP(int ndx)
	{
		return _src.getOpMinP(_ndx[ndx]);
	}

	@Override
	public void setOpMinP(int ndx, float mw)
	{
		_src.setOpMinP(_ndx[ndx], mw);
	}

	@Override
	public float[] getOpMinP()
	{
		return mapFloat(_src.getOpMinP());
	}

	@Override
	public void setOpMinP(float[] mw)
	{
		for(int i=0; i < _size; ++i)
			_src.setOpMinP(_ndx[i], mw[i]);
	}

	@Override
	public float getOpMaxP(int ndx)
	{
		return _src.getOpMaxP(_ndx[ndx]);
	}

	@Override
	public void setOpMaxP(int ndx, float mw)
	{
		_src.setOpMaxP(_ndx[ndx], mw);
	}

	@Override
	public float[] getOpMaxP()
	{
		return mapFloat(_src.getOpMaxP());
	}

	@Override
	public void setOpMaxP(float[] mw)
	{
		for(int i=0; i < _size; ++i)
			_src.setOpMaxP(_ndx[i], mw[i]);
	}

	@Override
	public float getMinQ(int ndx)
	{
		return _src.getMinQ(_ndx[ndx]);
	}

	@Override
	public void setMinQ(int ndx, float mvar)
	{
		_src.setMinQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getMinQ()
	{
		return mapFloat(_src.getMinQ());
	}

	@Override
	public void setMinQ(float[] mvar)
	{
		for(int i=0; i < _size; ++i)
			_src.setMinQ(_ndx[i], mvar[i]);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return _src.getMaxQ(_ndx[ndx]);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		_src.setMaxQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return mapFloat(_src.getMaxQ());
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxQ(_ndx[i], mvar[i]);
	}

	@Override
	public float getPS(int ndx)
	{
		return _src.getPS(_ndx[ndx]);
	}

	@Override
	public void setPS(int ndx, float mw)
	{
		_src.setPS(_ndx[ndx], mw);
	}

	@Override
	public float[] getPS()
	{
		return mapFloat(_src.getPS());
	}

	@Override
	public void setPS(float[] mw)
	{
		for(int i=0; i < _size; ++i)
			_src.setPS(_ndx[i], mw[i]);
	}

	@Override
	public float getQS(int ndx)
	{
		return _src.getQS(_ndx[ndx]);
	}

	@Override
	public void setQS(int ndx, float mvar)
	{
		_src.setQS(_ndx[ndx], mvar);
	}

	@Override
	public float[] getQS()
	{
		return mapFloat(_src.getQS());
	}

	@Override
	public void setQS(float[] mvar)
	{
		for(int i=0; i < _size; ++i)
			_src.setQS(_ndx[i], mvar[i]);
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
}
