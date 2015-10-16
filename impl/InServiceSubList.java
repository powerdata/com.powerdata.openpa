package com.powerdata.openpa.impl;

import java.util.Set;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.InService;
import com.powerdata.openpa.InServiceList;
import com.powerdata.openpa.PAModelException;

public abstract class InServiceSubList<T extends InService> extends SubList<T> implements
		InServiceList<T>
{
	InServiceList<T> _src;
	
	public InServiceSubList(InServiceList<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public boolean isInService(int ndx) throws PAModelException
	{
		return _src.isInService(_ndx[ndx]);
	}

	@Override
	public void setInService(int ndx, boolean state) throws PAModelException
	{
		_src.setInService(_ndx[ndx], state);
	}

	@Override
	public boolean[] isInService() throws PAModelException
	{
		return mapBool(_src.isInService());
	}

	@Override
	public void setInService(boolean[] state) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setInService(_ndx[i], state[i]);
	}

	@Override
	public Set<ColumnMeta> getColTypes()
	{
		return _src.getColTypes();
	}

	
	
}
