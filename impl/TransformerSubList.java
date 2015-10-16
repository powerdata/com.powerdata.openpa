package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TransformerList;

public class TransformerSubList extends ACBranchSubListBase<Transformer> implements TransformerList
{
	TransformerList _src;
	
	public TransformerSubList(TransformerList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Transformer get(int index)
	{
		return new Transformer(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Transformer;
	}

	@Override
	public boolean isRegEnabled(int ndx) throws PAModelException
	{
		return _src.isRegEnabled(_ndx[ndx]);
	}

	@Override
	public void setRegEnabled(int ndx, boolean enabl) throws PAModelException
	{
		_src.setRegEnabled(_ndx[ndx], enabl);
	}

	@Override
	public boolean[] isRegEnabled() throws PAModelException
	{
		return mapBool(_src.isRegEnabled());
	}

	@Override
	public void setRegEnabled(boolean[] enabl) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setRegEnabled(_ndx[i], enabl[i]);
	}

	@Override
	public Bus getTapBus(int ndx) throws PAModelException
	{
		return _src.getTapBus(_ndx[ndx]);
	}

	@Override
	public Bus[] getTapBus() throws PAModelException
	{
		return mapObject(_src.getTapBus());
	}

	@Override
	public void setTapBus(int ndx, Bus s) throws PAModelException
	{
		_src.setTapBus(_ndx[ndx], s);
	}

	@Override
	public void setTapBus(Bus[] s) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setTapBus(_ndx[i], s[i]);
	}

	@Override
	public float getMinKV(int ndx) throws PAModelException
	{
		return _src.getMinKV(_ndx[ndx]);
	}

	@Override
	public void setMinKV(int ndx, float kv) throws PAModelException
	{
		_src.setMinKV(_ndx[ndx], kv);
	}

	@Override
	public float[] getMinKV() throws PAModelException
	{
		return mapFloat(_src.getMinKV());
	}
	

	@Override
	public void setMinKV(float[] kv) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMinKV(_ndx[i], kv[i]);
	}

	@Override
	public float getMaxKV(int ndx) throws PAModelException
	{
		return _src.getMaxKV(_ndx[ndx]);
	}

	@Override
	public void setMaxKV(int ndx, float kv) throws PAModelException
	{
		_src.setMaxKV(_ndx[ndx], kv);
	}

	@Override
	public float[] getMaxKV() throws PAModelException
	{
		return mapFloat(_src.getMaxKV());
	}

	@Override
	public void setMaxKV(float[] kv) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxKV(_ndx[i], kv[i]);
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
	public boolean hasLTC(int ndx) throws PAModelException
	{
		return _src.hasLTC(_ndx[ndx]);
	}
	

	@Override
	public boolean[] hasLTC() throws PAModelException
	{
		return mapBool(_src.hasLTC());
	}

	@Override
	public void setHasLTC(int ndx, boolean b) throws PAModelException
	{
		_src.setHasLTC(_ndx[ndx], b);
	}

	@Override
	public void setHasLTC(boolean[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setHasLTC(_ndx[i], b[i]);
	}

	@Override
	public float getFromMinTap(int ndx) throws PAModelException
	{
		return _src.getFromMinTap(_ndx[ndx]);
	}

	@Override
	public void setFromMinTap(int ndx, float a) throws PAModelException
	{
		_src.setFromMinTap(_ndx[ndx], a);
	}

	@Override
	public float[] getFromMinTap() throws PAModelException
	{
		return mapFloat(_src.getFromMinTap());
	}

	@Override
	public void setFromMinTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromMinTap(_ndx[i], a[i]);
	}

	@Override
	public float getToMinTap(int ndx) throws PAModelException
	{
		return _src.getToMinTap(_ndx[ndx]);
	}

	@Override
	public float[] getToMinTap() throws PAModelException
	{
		return mapFloat(_src.getToMinTap());
	}

	@Override
	public void setToMinTap(int ndx, float a) throws PAModelException
	{
		_src.setToMinTap(_ndx[ndx], a);
	}

	@Override
	public void setToMinTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToMinTap(_ndx[i], a[i]);
	}

	@Override
	public float getFromMaxTap(int ndx) throws PAModelException
	{
		return _src.getFromMaxTap(_ndx[ndx]);
	}

	@Override
	public float[] getFromMaxTap() throws PAModelException
	{
		return mapFloat(_src.getFromMaxTap());
	}

	@Override
	public void setFromMaxTap(int ndx, float a) throws PAModelException
	{
		_src.setFromMaxTap(_ndx[ndx], a);
	}

	@Override
	public void setFromMaxTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromMaxTap(_ndx[i], a[i]);
	}

	@Override
	public float getToMaxTap(int ndx) throws PAModelException
	{
		return _src.getToMaxTap(_ndx[ndx]);
	}

	@Override
	public float[] getToMaxTap() throws PAModelException
	{
		return mapFloat(_src.getToMaxTap());
	}

	@Override
	public void setToMaxTap(int ndx, float a) throws PAModelException
	{
		_src.setToMaxTap(_ndx[ndx], a);
	}

	@Override
	public void setToMaxTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToMaxTap(_ndx[i], a[i]);
	}

	@Override
	public float getFromStepSize(int ndx) throws PAModelException
	{
		return _src.getFromStepSize(_ndx[ndx]);
	}

	@Override
	public float[] getFromStepSize() throws PAModelException
	{
		return mapFloat(_src.getFromStepSize());
	}

	@Override
	public void setFromStepSize(int ndx, float step) throws PAModelException
	{
		_src.setFromStepSize(_ndx[ndx], step);
	}

	@Override
	public void setFromStepSize(float[] step) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromStepSize(_ndx[i], step[i]);
	}

	@Override
	public float getToStepSize(int ndx) throws PAModelException
	{
		return _src.getToStepSize(_ndx[ndx]);
	}

	@Override
	public float[] getToStepSize() throws PAModelException
	{
		return mapFloat(_src.getToStepSize());
	}

	@Override
	public void setToStepSize(int ndx, float step) throws PAModelException
	{
		_src.setToStepSize(_ndx[ndx], step);
	}

	@Override
	public void setToStepSize(float[] step) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToStepSize(_ndx[i], step[i]);
	}
	

}
