package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.ListMetaType;

public class GenSubList extends OneTermDevSubList<Gen> implements GenList
{
	GenList _src;
	
	public GenSubList(GenList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Type getType(int ndx) throws PAModelException
	{
		return _src.getType(_ndx[ndx]);
	}

	@Override
	public void setType(int ndx, Type t) throws PAModelException
	{
		_src.setType(_ndx[ndx], t);
	}

	@Override
	public Type[] getType() throws PAModelException
	{
		return mapObject(_src.getType());
	}

	@Override
	public void setType(Type[] t) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setType(_ndx[i], t[i]);
	}

	@Override
	public Mode getMode(int ndx) throws PAModelException
	{
		return _src.getMode(_ndx[ndx]);
	}

	@Override
	public void setMode(int ndx, Mode m) throws PAModelException
	{
		_src.setMode(_ndx[ndx], m);
	}

	@Override
	public Mode[] getMode() throws PAModelException
	{
		return mapObject(_src.getMode());
	}

	@Override
	public void setMode(Mode[] m) throws PAModelException
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
	public float getOpMinP(int ndx) throws PAModelException
	{
		return _src.getOpMinP(_ndx[ndx]);
	}

	@Override
	public void setOpMinP(int ndx, float mw) throws PAModelException
	{
		_src.setOpMinP(_ndx[ndx], mw);
	}

	@Override
	public float[] getOpMinP() throws PAModelException
	{
		return mapFloat(_src.getOpMinP());
	}

	@Override
	public void setOpMinP(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOpMinP(_ndx[i], mw[i]);
	}

	@Override
	public float getOpMaxP(int ndx) throws PAModelException
	{
		return _src.getOpMaxP(_ndx[ndx]);
	}

	@Override
	public void setOpMaxP(int ndx, float mw) throws PAModelException
	{
		_src.setOpMaxP(_ndx[ndx], mw);
	}

	@Override
	public float[] getOpMaxP() throws PAModelException
	{
		return mapFloat(_src.getOpMaxP());
	}

	@Override
	public void setOpMaxP(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOpMaxP(_ndx[i], mw[i]);
	}

	@Override
	public float getMinQ(int ndx) throws PAModelException
	{
		return _src.getMinQ(_ndx[ndx]);
	}

	@Override
	public void setMinQ(int ndx, float mvar) throws PAModelException
	{
		_src.setMinQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getMinQ() throws PAModelException
	{
		return mapFloat(_src.getMinQ());
	}

	@Override
	public void setMinQ(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMinQ(_ndx[i], mvar[i]);
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
	public float getPS(int ndx) throws PAModelException
	{
		return _src.getPS(_ndx[ndx]);
	}

	@Override
	public void setPS(int ndx, float mw) throws PAModelException
	{
		_src.setPS(_ndx[ndx], mw);
	}

	@Override
	public float[] getPS() throws PAModelException
	{
		return mapFloat(_src.getPS());
	}

	@Override
	public void setPS(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setPS(_ndx[i], mw[i]);
	}

	@Override
	public float getQS(int ndx) throws PAModelException
	{
		return _src.getQS(_ndx[ndx]);
	}

	@Override
	public void setQS(int ndx, float mvar) throws PAModelException
	{
		_src.setQS(_ndx[ndx], mvar);
	}

	@Override
	public float[] getQS() throws PAModelException
	{
		return mapFloat(_src.getQS());
	}

	@Override
	public void setQS(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setQS(_ndx[i], mvar[i]);
	}

	@Override
	public boolean isRegKV(int ndx) throws PAModelException
	{
		return _src.isRegKV(_ndx[ndx]);
	}

	@Override
	public void setRegKV(int ndx, boolean reg) throws PAModelException
	{
		_src.setRegKV(_ndx[ndx], reg);
	}

	@Override
	public boolean[] isRegKV() throws PAModelException
	{
		return mapBool(_src.isRegKV());
	}

	@Override
	public void setRegKV(boolean[] reg) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setRegKV(_ndx[i], reg[i]);
	}

	@Override
	public float getVS(int ndx) throws PAModelException
	{
		return _src.getVS(_ndx[ndx]);
	}

	@Override
	public void setVS(int ndx, float kv) throws PAModelException
	{
		_src.setVS(_ndx[ndx], kv);
	}

	@Override
	public float[] getVS() throws PAModelException
	{
		return mapFloat(_src.getVS());
	}

	@Override
	public void setVS(float[] kv) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setVS(_ndx[i], kv[i]);
	}

	@Override
	public Bus getRegBus(int ndx) throws PAModelException
	{
		return _src.getRegBus(_ndx[ndx]);
	}

	@Override
	public void setRegBus(int ndx, Bus b) throws PAModelException
	{
		_src.setRegBus(_ndx[ndx], b);
	}

	@Override
	public Bus[] getRegBus() throws PAModelException
	{
		return mapObject(_src.getRegBus());
	}

	@Override
	public void setRegBus(Bus[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setRegBus(_ndx[i], b[i]);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Gen;
	}
}
