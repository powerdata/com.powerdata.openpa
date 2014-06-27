package com.powerdata.openpa;

import java.util.AbstractList;
import com.powerdata.openpa.tools.SNdxKeyOfs;

/**
 * Base of the object list hierarchy
 * 
 * @author chris@powerdata.com
 * 
 */

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	/** read only */
	private static final int RO = 0;
	/** read-write */
	private static final int RW = 1;
	
	PALists     _model;
	String[][] _id = IStr(), _name = IStr();
	SNdxKeyOfs _keyndx = null;
	int _size, _keys[] = null;
	
	protected static final String[][] IStr() {return new String[2][];}
	protected static final float[][] IFlt() {return new float[2][];}
	protected static final int[][] IInt() {return new int[2][];}
	
	protected final void setStr(String[][] v, int ndx, String s)
	{
		String[] rw = v[RW];
		if (rw == null)
		{
			rw = new String[_size];
			v[RW] = rw;
		}
		if (v[RO] == null) v[RO] = rw.clone();
		rw[ndx] = s;
	}
	
	protected final String[] getStr(String[][] v)
	{
		String[] rw = v[RW];
		if (v[RO] == null) v[RO] = rw.clone();
		return rw;
	}
	
	protected final String getStr(String[][] v, int ndx)
	{
		return v[RW][ndx];
	}
	
	protected final void setStr(String[][] v, String[] s)
	{
		if (v[RO] == null && v[RW] != null)
			v[RO] = v[RW].clone();
		v[RW] = s;
	}
	
	protected final void setFloat(float[][] v, int ndx, float s)
	{
		float[] rw = v[RW];
		if (rw == null)
		{
			rw = new float[_size];
			v[RW] = rw;
		}
		if (v[RO] == null) v[RO] = rw.clone();
		rw[ndx] = s;
	}
	
	protected final float[] getFloat(float[][] v)
	{
		float[] rw = v[RW];
		if (v[RO] == null) v[RO] = rw.clone();
		return rw;
	}

	protected final float getFloat(float[][] v, int ndx)
	{
		return v[RW][ndx];
	}

	protected final void setFloat(float[][] v, float[] s)
	{
		if (v[RO] == null && v[RW] != null)
			v[RO] = v[RW].clone();
		v[RW] = s;
	}

	protected final void setInt(int[][] v, int ndx, int s)
	{
		int[] rw = v[RW];
		if (rw == null)
		{
			rw = new int[_size];
			v[RW] = rw;
		}
		if (v[RO] == null) v[RO] = rw.clone();
		rw[ndx] = s;
	}
	
	protected final int[] getInt(int[][] v)
	{
		int[] rw = v[RW];
		if (v[RO] == null) v[RO] = rw.clone();
		return rw;
	}
	
	protected final int getInt(int[][] v, int ndx)
	{
		return v[RW][ndx];
	}

	protected final void setInt(int[][] v, int[] s)
	{
		if (v[RO] == null && v[RW] != null)
			v[RO] = v[RW].clone();
		v[RW] = s;
	}

	protected BaseList(PALists model, int size)
	{
		_model = model;
		_size = size;
		_keys = new int[size];
		for(int i=0; i < size; ++i)
			_keys[i] = i+1;
		_keyndx = new SNdxKeyOfs()
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

			@Override
			public int[] getKeys()
			{
				return _keys;
			}};
		
	}
	
	protected BaseList(PALists model, int[] keys)
	{
		_model = model;
		_size = keys.length;
		_keys = keys;
		_keyndx = SNdxKeyOfs.Create(getKey());
	}
	
	protected BaseList()
	{
		_size = 0;
	}

	@Override
	public int size()
	{
		return _size;
	}
	
	@Nodump
	public T getByKey(int key)
	{
		return get(getIndex(key));
	}
	
	@Nodump
	public int getIndex(int key)
	{
		return _keyndx.getOffset(key);
	}
	
	@Nodump
	public int[] getIndexes(int[] keys)
	{
		return _keyndx.getOffsets(keys);
	}

	/** get unique object key */
	public int getKey(int ndx)
	{
		return _keys[ndx];
	}
	
	/** Return array of keys */
	public int[] getKey()
	{
		return _keys;
	}

	/** get unique object ID */
	public String getID(int ndx)
	{
		return getStr(_id, ndx);
	}

	/** set unique object ID */
	public void setID(int ndx, String id)
	{
		setStr(_id, ndx, id);
	}

	/** return array of string object ID's */
	public String[] getID()
	{
		return getStr(_id);
	}
	/** set unique object ID */
	public void setID(String[] id)
	{
		setStr(_id, id);
	}

	/** name of object */
	public String getName(int ndx)
	{
		return getStr(_name, ndx);
	}
	/** set name of object */
	public void setName(int ndx, String name)
	{
		setStr(_name, ndx, name);
	}
	/** name of object */
	public String[] getName()
	{
		return getStr(_name);
	}
	/** set name of object */
	public void setName(String[] name)
	{
		setStr(_name, name);
	}

	public T[] toArray(int[] indexes)
	{
		T[] us = newarray(_size);
		for(int i=0; i < _size; ++i)
			us[i] = get(i);
		int n = indexes.length;
		T[] rv = newarray(n);
		for(int i=0; i < n; ++i)
			rv[i] = us[indexes[i]];
		return rv;
	}
	
	protected abstract T[] newarray(int size);

	protected int[] getKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getKey(offsets[i]);
		return rv;
	}
	
	/** Name access for "sublist" subclasses to use */
	protected String[] getSubListNames(int[] slndx)
	{
		int n = size();
		String[] rv = new String[n];
		for(int i=0; i < n; ++i)
			rv[i] = getName(slndx[i]);
		return rv;
	}
	/** Name access for "sublist" subclasses to use */
	protected void setSubListNames(String[] names, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setName(slndx[i], names[i]);
	}
	
	/** ID access for "sublist" subclasses to use */
	protected String[] getSubListIDs(int[] slndx)
	{
		int n = size();
		String[] rv = new String[n];
		for(int i=0; i < n; ++i)
			rv[i] = getID(slndx[i]);
		return rv;
	}
	/** ID access for "sublist" subclasses to use */
	protected void setSubListIDs(String[] ids, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setID(slndx[i], ids[i]);
	}
	
	protected int[] objectNdx(BaseObject[] objects)
	{
		int n = objects.length;
		int[] s = new int[n];
		for(int i=0; i < n; ++i) s[i] = objects[i].getIndex();
		return s;
	}

}
