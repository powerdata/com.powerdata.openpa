package com.powerdata.openpa;

import java.lang.reflect.Array;
import java.util.Set;
import com.powerdata.openpa.PAModel.ColAccess;
import com.powerdata.openpa.PAModel.HasMetaList;
import com.powerdata.openpa.PAModel.ListMetaType;

/**
 * Base of the object list hierarchy
 * 
 * @author chris@powerdata.com
 * 
 */

public abstract class AbstractPAList<T extends BaseObject> extends AbstractBaseList<T> 
{
	interface PAListMeta
	{
		Enum<? extends HasMetaList> getID();
		Enum<? extends HasMetaList> getName();
	}
	
	protected abstract PAListMeta getMeta();
	
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
	
	protected abstract ListMetaType getMetaType();
	
	protected final <U> U getObj(U[][] v, int ndx)
	{
		return v[RW][ndx];
	}
	
	@SuppressWarnings("unchecked")
	protected final <U> void setObj(U[][] v, int ndx, U s, Enum<? extends HasMetaList> c)
	{
		U[] rw = v[RW];
		if (rw == null)
		{
			rw = (U[]) Array.newInstance(s.getClass(), _size);
			v[RW] = rw;
		}
		
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
		rw[ndx] = s;
	}
	
	protected final <U> U[] getObj(U[][] v, Enum<? extends HasMetaList> c)
	{
		U[] rw = v[RW];
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
		return rw;
	}
	
	protected final <U> void setObj(U[][] v, U[] s)
	{
//		if (v[RO] == null && v[RW] != null)
//			v[RO] = v[RW].clone();
		v[RW] = s.clone();
	}
	
	protected final void setFloat(float[][] v, int ndx, float s, Enum<? extends HasMetaList> c)
	{
		float[] rw = v[RW];
		if (rw == null)
		{
			rw = new float[_size];
			v[RW] = rw;
		}
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
		rw[ndx] = s;
	}
	
	protected final float[] getFloat(float[][] v, Enum<? extends HasMetaList> c)
	{
		float[] rw = v[RW];
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
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

	protected final void setInt(int[][] v, int ndx, int s, Enum<? extends HasMetaList> c)
	{
		int[] rw = v[RW];
		if (rw == null)
		{
			rw = new int[_size];
			v[RW] = rw;
		}
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
		rw[ndx] = s;
	}
	
	protected final int[] getInt(int[][] v, Enum<? extends HasMetaList> c)
	{
		int[] rw = v[RW];
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
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

	protected final void setBool(boolean[][] v, int ndx, boolean s, Enum<? extends HasMetaList> c)
	{
		boolean[] rw = v[RW];
		if (rw == null)
		{
			rw = new boolean[_size];
			v[RW] = rw;
		}
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
		rw[ndx] = s;
	}
	
	protected final boolean[] getBool(boolean[][] v, Enum<? extends HasMetaList> c)
	{
		boolean[] rw = v[RW];
		if (v[RO] == null)
		{
			v[RO] = rw.clone();
			_model.setChange(c);
		}
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
		setObj(_id, ndx, id, getMeta().getID());
	}

	/** return array of string object ID's */
	@Override
	public String[] getID()
	{
		return getObj(_id, getMeta().getID());
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
		setObj(_name, ndx, name, getMeta().getName());
	}
	/** name of object */
	@Override
	public String[] getName()
	{
		return getObj(_name, getMeta().getName());
	}
	/** set name of object */
	@Override
	public void setName(String[] name)
	{
		setObj(_name, name);
	}

	public int[] getKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getKey(offsets[i]);
		return rv;
	}

	protected void collectChanges(Set<ColAccess> rv)
	{
		
		
	}
	
	protected void clearChanges()
	{
		_name[RO] = null;
	}
	
}
