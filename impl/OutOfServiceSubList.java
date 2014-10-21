package com.powerdata.openpa.impl;

import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.OutOfServiceList;
import com.powerdata.openpa.PAModelException;

public abstract class OutOfServiceSubList<T extends OutOfService> extends SubList<T> implements
		OutOfServiceList<T>
{
	OutOfServiceList<T> _src;
	
	public OutOfServiceSubList(OutOfServiceList<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
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
