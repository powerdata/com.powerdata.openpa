package com.powerdata.openpa;

import java.lang.reflect.Array;

public abstract class SubList<T extends BaseObject> extends AbstractBaseList<T>
{
	protected int _size;
	BaseList<T> _src;
	int[] _ndx;
	
	public SubList(BaseList<T> src, int[] ndx)
	{
		super(makeKeys(src, ndx));
		_src = src;
		_ndx = ndx;
		_size = ndx.length;
	}
	
	private static <U extends BaseObject> int[] makeKeys(BaseList<U> src, int[] ndx)
	{
		int size = ndx.length;
		int[] newkeys = new int[size];
		for(int i=0; i < size; ++i)
			newkeys[i] = src.getKey(ndx[i]);
		return newkeys;
	}

	@Override
	public String getID(int ndx)
	{
		return _src.getID(_ndx[ndx]);
	}

	@Override
	public String[] getID()
	{
		return mapObject(_src.getID());
	}	

	@Override
	public void setID(int ndx, String id)
	{
		_src.setID(_ndx[ndx], id);
	}

	@Override
	public void setID(String[] id)
	{
		for(int i=0; i < _size; ++i)
			_src.setID(_ndx[i], id[i]);
	}


	@Override
	public String getName(int ndx)
	{
		return _src.getName(_ndx[ndx]);
	}

	@Override
	public void setName(int ndx, String name)
	{
		_src.setName(_ndx[ndx], name);
	}

	@Override
	public String[] getName()
	{
		return mapObject(_src.getName());
	}

	@Override
	public void setName(String[] name)
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

}
