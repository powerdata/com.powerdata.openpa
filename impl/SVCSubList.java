package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVCList;

public class SVCSubList extends OneTermDevSubList<SVC> implements SVCList
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
	public float getMinB(int ndx) throws PAModelException
	{
		return _src.getMinB(_ndx[ndx]);
	}

	@Override
	public void setMinB(int ndx, float b) throws PAModelException
	{
		_src.setMinB(_ndx[ndx], b);
	}

	@Override
	public float[] getMinB() throws PAModelException
	{
		return mapFloat(_src.getMinB());
	}

	@Override
	public void setMinB(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMinB(_ndx[i], b[i]);
	}

	@Override
	public float getMaxB(int ndx) throws PAModelException
	{
		return _src.getMaxB(_ndx[ndx]);
	}

	@Override
	public void setMaxB(int ndx, float b) throws PAModelException
	{
		_src.setMaxB(_ndx[ndx], b);
	}

	@Override
	public float[] getMaxB() throws PAModelException
	{
		return mapFloat(_src.getMaxB());
	}

	@Override
	public void setMaxB(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxB(_ndx[i], b[i]);
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
		return ListMetaType.SVC;
	}

	@Override
	public float getSlope(int ndx) throws PAModelException
	{
		return _src.getSlope(_ndx[ndx]);
	}

	@Override
	public void setSlope(int ndx, float slope) throws PAModelException
	{
		_src.setSlope(_ndx[ndx], slope);
	}

	@Override
	public float[] getSlope() throws PAModelException
	{
		return mapFloat(_src.getSlope());
	}

	@Override
	public void setSlope(float[] slope) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setSlope(_ndx[i], slope[i]);
	}

}
