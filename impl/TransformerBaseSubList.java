package com.powerdata.openpa.impl;

import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TransformerBaseList;

public abstract class TransformerBaseSubList<T extends TransformerBase> extends ACBranchSubList<T> implements TransformerBaseList<T>
{
	TransformerBaseList<T> _src;
	
	public TransformerBaseSubList(TransformerBaseList<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}


	@Override
	public float getGmag(int ndx) throws PAModelException
	{
		return _src.getGmag(_ndx[ndx]);
	}

	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		_src.setGmag(_ndx[ndx], g);
	}

	@Override
	public float[] getGmag() throws PAModelException
	{
		return mapFloat(_src.getGmag());
	}

	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setGmag(_ndx[i], g[i]);
	}

	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		return _src.getBmag(_ndx[ndx]);
	}

	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		_src.setBmag(_ndx[ndx], b);
	}

	@Override
	public float[] getBmag() throws PAModelException
	{
		return mapFloat(_src.getBmag());
	}

	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setBmag(_ndx[i], b[i]);
	}

	@Override
	public float getShift(int ndx) throws PAModelException
	{
		return _src.getShift(_ndx[ndx]);
	}

	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		_src.setShift(_ndx[ndx], sdeg);
	}

	@Override
	public float[] getShift() throws PAModelException
	{
		return mapFloat(_src.getShift());
	}

	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setShift(_ndx[i], sdeg[i]);
	}

	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		return _src.getFromTap(_ndx[ndx]);
	}

	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		_src.setFromTap(_ndx[ndx], a);
	}

	@Override
	public float[] getFromTap() throws PAModelException
	{
		return mapFloat(_src.getFromTap());
	}

	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromTap(_ndx[i], a[i]);
	}

	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		return _src.getToTap(_ndx[ndx]);
	}

	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		_src.setToTap(_ndx[ndx], a);
	}

	@Override
	public float[] getToTap() throws PAModelException
	{
		return mapFloat(_src.getToTap());
	}

	@Override
	public void setToTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToTap(_ndx[i], a[i]);
	}
}
