package com.powerdata.openpa.impl;

import java.util.AbstractList;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;

public abstract class AbstractBaseList<T extends BaseObject> extends AbstractList<T> implements BaseList<T>
{
	protected int _size;
	
	protected AbstractBaseList(int size)
	{
		System.out.println("[AbstractBaseList.java]");
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
	

}
