package com.powerdata.openpa.impl;

import java.util.AbstractList;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;

public abstract class AbstractBaseList<T extends BaseObject> extends AbstractList<T> implements BaseList<T>
{
	protected int _size;
	
	protected AbstractBaseList(int size)
	{
		_size = size;
	}
	
	protected AbstractBaseList()
	{
		_size = 0;
	}

	@Override
	public int size()
	{
		return _size;
	}
	
	@Override
	public boolean objEquals(int ndx, Object obj)
	{
		BaseObject o = (BaseObject) obj;
		return getListMeta().equals(o.getList().getListMeta()) && getKey(ndx) == o.getKey();
	}

	@Override
	public int objHash(int ndx)
	{
		return BaseList.CalcListHash(getListMeta(), getKey(ndx));
	}
	
}
