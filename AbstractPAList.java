package com.powerdata.openpa;

import java.lang.reflect.Array;

/**
 * Base of the object list hierarchy
 * 
 * @author chris@powerdata.com
 * 
 */

public abstract class AbstractPAList<T extends BaseObject> extends AbstractBaseList<T> 
{
	/** read only */
	protected static final int RO = 0;
	/** read-write */
	protected static final int RW = 1;
	
	PAModel     _model;
	String[][] _id = IStr(), _name = IStr();
	
	protected static final String[][] IStr() {return new String[2][];}
	protected static final float[][] IFlt() {return new float[2][];}
	protected static final int[][] IInt() {return new int[2][];}
	protected static final boolean[][] IBool() {return new boolean[2][];}
	
	protected final <U> U[] getObj(U[][] v)
	{
		U[] rw = v[RW];
		if (v[RO] == null) v[RO] = rw.clone();
		return rw;
	}
	
	@SuppressWarnings("unchecked")
	protected final <U> void setObj(U[][] v, int ndx, U s)
	{
		U[] rw = v[RW];
		if (rw == null)
		{
			rw = (U[]) Array.newInstance(s.getClass(), _size);
			v[RW] = rw;
		}
		if (v[RO] == null) v[RO] = rw.clone();
		rw[ndx] = s;
	}
	
	protected final <U> U getObj(U[][] v, int ndx)
	{
		return v[RW][ndx];
	}
	
	protected final <U> void setObj(U[][] v, U[] s)
	{
		if (v[RO] == null && v[RW] != null)
			v[RO] = v[RW].clone();
		v[RW] = s.clone();
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
		v[RW] = s.clone();
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
		v[RW] = s.clone();
	}

	protected final void setBool(boolean[][] v, int ndx, boolean s)
	{
		boolean[] rw = v[RW];
		if (rw == null)
		{
			rw = new boolean[_size];
			v[RW] = rw;
		}
		if (v[RO] == null) v[RO] = rw.clone();
		rw[ndx] = s;
	}
	
	protected final boolean[] getBool(boolean[][] v)
	{
		boolean[] rw = v[RW];
		if (v[RO] == null) v[RO] = rw.clone();
		return rw;
	}
	
	protected final boolean getBool(boolean[][] v, int ndx)
	{
		return v[RW][ndx];
	}

	protected final void setBool(boolean[][] v, boolean[] s)
	{
		if (v[RO] == null && v[RW] != null)
			v[RO] = v[RW].clone();
		v[RW] = s.clone();
	}

	AbstractPAList(PAModel model, int size)
	{
		super(size);
		_model = model;
	}
	
	protected AbstractPAList(PAModel model, int[] keys)
	{
		super(keys);
		_model = model;
	}
	
	protected AbstractPAList()
	{
		_size = 0;
	}

	@Override
	public int size()
	{
		return _size;
	}
	
	/** get unique object ID */
	@Override
	public String getID(int ndx)
	{
		return getObj(_id, ndx);
	}

	/** set unique object ID */
	@Override
	public void setID(int ndx, String id)
	{
		setObj(_id, ndx, id);
	}

	/** return array of string object ID's */
	@Override
	public String[] getID()
	{
		return getObj(_id);
	}
	/** set unique object ID */
	@Override
	public void setID(String[] id)
	{
		setObj(_id, id);
	}

	/** name of object */
	@Override
	public String getName(int ndx)
	{
		return getObj(_name, ndx);
	}
	/** set name of object */
	@Override
	public void setName(int ndx, String name)
	{
		setObj(_name, ndx, name);
	}
	/** name of object */
	@Override
	public String[] getName()
	{
		return getObj(_name);
	}
	/** set name of object */
	@Override
	public void setName(String[] name)
	{
		setObj(_name, name);
	}

	protected int[] getKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getKey(offsets[i]);
		return rv;
	}

	
	
//	/** Name access for "sublist" subclasses to use */
//	protected String[] getSubListNames(int[] slndx)
//	{
//		int n = size();
//		String[] rv = new String[n];
//		for(int i=0; i < n; ++i)
//			rv[i] = getName(slndx[i]);
//		return rv;
//	}
//	/** Name access for "sublist" subclasses to use */
//	protected void setSubListNames(String[] names, int[] slndx)
//	{
//		int n = size();
//		for(int i=0; i < n; ++i)
//			setName(slndx[i], names[i]);
//	}
//	
//	/** ID access for "sublist" subclasses to use */
//	protected String[] getSubListIDs(int[] slndx)
//	{
//		int n = size();
//		String[] rv = new String[n];
//		for(int i=0; i < n; ++i)
//			rv[i] = getID(slndx[i]);
//		return rv;
//	}
//	/** ID access for "sublist" subclasses to use */
//	protected void setSubListIDs(String[] ids, int[] slndx)
//	{
//		int n = size();
//		for(int i=0; i < n; ++i)
//			setID(slndx[i], ids[i]);
//	}
	
}
