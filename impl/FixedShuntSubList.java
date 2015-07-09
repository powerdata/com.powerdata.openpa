package com.powerdata.openpa.impl;

import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.PAModelException;

public class FixedShuntSubList<T extends FixedShunt> extends OneTermDevSubList<T> implements FixedShuntListIfc<T>
{
	FixedShuntListIfc<T> _src;
	
	public FixedShuntSubList(FixedShuntListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getB(int ndx) throws PAModelException
	{
		return _src.getB(_ndx[ndx]);
	}

	@Override
	public void setB(int ndx, float b) throws PAModelException
	{
		_src.setB(_ndx[ndx], b);
	}

	@Override
	public float[] getB() throws PAModelException
	{
		return mapFloat(_src.getB());
	}

	@Override
	public void setB(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setB(_ndx[i], b[i]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new FixedShunt(this, index);
	}

}
