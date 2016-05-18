package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
