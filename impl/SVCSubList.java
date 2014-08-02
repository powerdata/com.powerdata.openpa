package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVC.SVCState;
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
	public float getMinQ(int ndx) throws PAModelException
	{
		return _src.getMinQ(_ndx[ndx]);
	}

	@Override
	public void setMinQ(int ndx, float b) throws PAModelException
	{
		_src.setMinQ(_ndx[ndx], b);
	}

	@Override
	public float[] getMinQ() throws PAModelException
	{
		return mapFloat(_src.getMinQ());
	}

	@Override
	public void setMinQ(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMinQ(_ndx[i], b[i]);
	}

	@Override
	public float getMaxQ(int ndx) throws PAModelException
	{
		return _src.getMaxQ(_ndx[ndx]);
	}

	@Override
	public void setMaxQ(int ndx, float b) throws PAModelException
	{
		_src.setMaxQ(_ndx[ndx], b);
	}

	@Override
	public float[] getMaxQ() throws PAModelException
	{
		return mapFloat(_src.getMaxQ());
	}

	@Override
	public void setMaxQ(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxQ(_ndx[i], b[i]);
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

	@Override
	public SVCState getOutputMode(int ndx) throws PAModelException
	{
		return _src.getOutputMode(_ndx[ndx]);
	}

	@Override
	public SVCState[] getOutputMode() throws PAModelException
	{
		return this.mapObject(_src.getOutputMode());
	}

	@Override
	public void setOutputMode(int ndx, SVCState m) throws PAModelException
	{
		_src.setOutputMode(_ndx[ndx], m);
	}

	@Override
	public void setOutputMode(SVCState[] m) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOutputMode(_ndx[i], m[i]);
	}

	@Override
	public float getQS(int ndx) throws PAModelException
	{
		return _src.getQS(_ndx[ndx]);
	}

	@Override
	public float[] getQS() throws PAModelException
	{
		return mapFloat(_src.getQS());
	}

	@Override
	public void setQS(int ndx, float mvar) throws PAModelException
	{
		_src.setQS(_ndx[ndx], mvar);
	}

	@Override
	public void setQS(float[] mvar) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setQS(_ndx[i], mvar[i]);
	}

}
