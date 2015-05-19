package com.powerdata.openpa.impl;

import java.lang.reflect.Array;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;

public abstract class SubList<T extends BaseObject> extends AbstractBaseList<T>
{
	protected int _size;
	BaseList<T> _src;
	int[] _ndx;
	
	public SubList(BaseList<T> src, int[] ndx)
	{
		super(ndx.length);
		_src = src;
		_ndx = ndx;
		_size = ndx.length;
	}
	
	@Override
	public T getByID(String id) throws PAModelException 
	{
		return _src.getByID(id);
	}
	
	@Override
	public String getID(int ndx) throws PAModelException
	{
		return _src.getID(_ndx[ndx]);
	}

	@Override
	public String[] getID() throws PAModelException
	{
		return mapObject(_src.getID());
	}	

	@Override
	public void setID(int ndx, String id) throws PAModelException
	{
		_src.setID(_ndx[ndx], id);
	}

	@Override
	public void setID(String[] id) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setID(_ndx[i], id[i]);
	}

	@Override
	public String getName(int ndx) throws PAModelException
	{
		return _src.getName(_ndx[ndx]);
	}

	@Override
	public void setName(int ndx, String name) throws PAModelException
	{
		_src.setName(_ndx[ndx], name);
	}

	@Override
	public String[] getName() throws PAModelException
	{
		return mapObject(_src.getName());
	}

	@Override
	public void setName(String[] name) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setName(_ndx[i], name[i]);
	}
	@SuppressWarnings("unchecked")
	protected <U> U[] mapObject(U[] src)
	{
		Class<U> c = (Class<U>) Array.get(src, 0).getClass();
		U[] rv = (U[]) Array.newInstance(c, _size);
		for(int i=0; i < _size; ++i)
			rv[i] = src[_ndx[i]];
		return rv;
	}
	
	protected float[] mapFloat(float[] src)
	{
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = src[_ndx[i]];
		return rv;
	}

	protected int[] mapInt(int[] src)
	{
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = src[_ndx[i]];
		return rv;
	}

	protected boolean[] mapBool(boolean[] src)
	{
		boolean[] rv = new boolean[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = src[_ndx[i]];
		return rv;
	}

	@Override
	public int getKey(int ndx)
	{
		return _src.getKey(_ndx[ndx]);
	}

	@Override
	public int[] getKeys()
	{
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = _src.getKey(_ndx[i]);
		return rv;
	}

	@Override
	@Nodump
	public T getByKey(int key)
	{
		return _src.getByKey(key);
	}

	@Override
	public int getIndex(int ndx)
	{
		return _src.getIndex(_ndx[ndx]);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return _src.getListMeta();
	}
	
	
}
