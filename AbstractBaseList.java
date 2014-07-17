package com.powerdata.openpa;

import java.lang.reflect.Array;
import java.util.AbstractList;

import com.powerdata.openpa.tools.SNdxKeyOfs;

public abstract class AbstractBaseList<T extends BaseObject> extends AbstractList<T> implements BaseList<T>
{
	protected int _size;
	protected SNdxKeyOfs _keyndx;

	protected AbstractBaseList(int size)
	{
		_size = size;
		_keyndx = new SNdxNoKey(size);
	}
	
	protected AbstractBaseList(int[] keys)
	{
		_size = keys.length;
		_keyndx = SNdxKeyOfs.Create(keys);
	}
	
	protected AbstractBaseList()
	{
		_size = 0;
		_keyndx = null;
	}

	@Override
	public int size()
	{
		return _size;
	}
	
	@Override
	@Nodump
	public T getByKey(int key)
	{
		return get(_keyndx.getOffset(key));
	}
	
	/** get unique object key */
	@Override
	public int getKey(int ndx)
	{
		return _keyndx.getKey(ndx);
	}
	
	@Override
	public int[] getKeys()
	{
		return _keyndx.getKeys();
	}

	@Override
	public int[] getIndexesFromKeys(int[] keys)
	{
		return _keyndx.getOffsets(keys);
	}
	

	
	@Override
	public T[] toArray(int[] indexes)
	{
		T[] us = newArray(_size);
		for(int i=0; i < _size; ++i)
			us[i] = get(i);
		int n = indexes.length;
		T[] rv = newArray(n);
		for(int i=0; i < n; ++i)
			rv[i] = us[indexes[i]];
		return rv;
	}
	
	@SuppressWarnings("unchecked")
	protected T[] newArray(int size)
	{
		return (T[]) Array.newInstance(get(0).getClass(), size);
	}

}
