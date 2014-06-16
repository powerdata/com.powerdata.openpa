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
	PALists     _model;
	String[]	_id, _idorig, _name, _nameorig;
	SNdxKeyOfs _keyndx = null;
	int _size, _keys[] = null;
	
	protected BaseList(PALists model, int size)
	{
		_model = model;
		_size = size;
		_keys = new int[size];
		for(int i=0; i < size; ++i)
			_keys[i] = i;
		_keyndx = new SNdxKeyOfs()
		{
			@Override
			public int size()
			{
				return _size;
			}

			@Override
			public boolean containsKey(int key)
			{
				return key >= 0 && key < _size;
			}

			@Override
			public int getOffset(int key)
			{
				return key;
			}

			@Override
			public int[] getKeys()
			{
				return _keys;
			}
			
			@Override
			public int getKey(int ndx)
			{
				return ndx;
			}};
	}
	
	protected BaseList(PALists model, int[] keys)
	{
		_model = model;
		_size = keys.length;
		_keys = keys;
		_keyndx = null;
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
	
	public T getByKey(int key)
	{
		return get(getOfs(key));
	}
	
	public int getOfs(int key)
	{
		if (_keyndx == null)
		{
			_keyndx = SNdxKeyOfs.Create(getKey());
		}
		return _keyndx.getOffset(key);
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
		return getID()[ndx];
	}

	/** set unique object ID */
	public void setID(int ndx, String id)
	{
		if (_idorig == null && _id != null)
		{
			_idorig = _id.clone();
		}
		_id[ndx] = id;
	}

	/** return array of string object ID's */
	public String[] getID()
	{
		return _id;
	}
	/** set unique object ID */
	public void setID(String[] id)
	{
		if (_idorig == null && _id != null &&  _id != id)
			_idorig = _id.clone();
		_id = id;
	}

	/** name of object */
	public String getName(int ndx)
	{
		return getName()[ndx];
	}
	/** set name of object */
	public void setName(int ndx, String name)
	{
		if (_nameorig == null && _name != null)
		{
			_nameorig = _name.clone();
		}
		_name[ndx] = name;
	}
	/** name of object */
	public String[] getName()
	{
		return _name;
	}
	/** set name of object */
	public void setName(String[] name)
	{
		if (_nameorig == null && _name != null && _name != name)
			_nameorig = _name.clone();
		_name = name;
	}

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
}
