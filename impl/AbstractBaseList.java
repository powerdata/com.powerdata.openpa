package com.powerdata.openpa.impl;

import java.lang.reflect.Array;
import java.util.AbstractList;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.tools.SNdxKeyOfs;

public abstract class AbstractBaseList<T extends BaseObject> extends AbstractList<T> implements BaseList<T>
{
	protected int _size;
	protected SNdxKeyOfs _keyndx;
	protected int[] _keys;
	protected KeyMgr _km;
	
	interface KeyMgr
	{
		int key(int ndx);
		int[] keys();
	}
	
	class NoKeyMgr implements KeyMgr
	{
		@Override
		public int[] keys()
		{
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i) rv[i] = i+1;
			return rv;
		}

		@Override
		public int key(int ndx)
		{
			return ndx+1;
		}
	}
	
	class YesKeyMgr implements KeyMgr
	{
		@Override
		public int key(int ndx)
		{
			return _keys[ndx];
		}

		@Override
		public int[] keys()
		{
			return _keys;
		}
	}

	class SNdxNoKey extends SNdxKeyOfs
	{
		@Override
		public int size() {return _size;}

		@Override
		public boolean containsKey(int key)
		{
			return key > 0 && key <= _size;
		}

		@Override
		public int getOffset(int key)
		{
			return key-1;
		}

		@Override
		public int[] getOffsets(int[] keys)
		{
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i) rv[i] = keys[i]-1;
			return rv;
		}

	};


	protected AbstractBaseList(int size)
	{
		_size = size;
		_keys = null;
		_km = new NoKeyMgr();
	}
	
	protected AbstractBaseList(int[] keys)
	{
		_keys = keys;
		_km = new YesKeyMgr();
		_size = keys.length;
	}
	
	protected AbstractBaseList()
	{
		_size = 0;
	}

	/** Set up keys in the event that the "key" constructor isn't used */
	void setupKeys(int[] keys)
	{
		_size = keys.length;
		_keys = keys;
		_km = new YesKeyMgr();
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
		if (_keyndx == null) mkKeyNdx();
		return get(_keyndx.getOffset(key));
	}
	
	void mkKeyNdx()
	{
		_keyndx = (_keys == null) ? new SNdxNoKey() : SNdxKeyOfs.Create(_keys);
	}

	/** get unique object key */
	@Override
	public int getKey(int ndx)
	{
		return _km.key(ndx);
	}
	
	@Override
	public int[] getKeys()
	{
		return _km.keys();
	}

	@Override
	public int[] getIndexesFromKeys(int[] keys)
	{
		if (_keyndx == null) mkKeyNdx();
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
