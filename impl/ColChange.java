package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;

abstract class ColChange implements com.powerdata.openpa.ColChange
{
	ListMetaType _lmeta;
	int[] _idx;
	int _size;
	private AbstractPAList<? extends BaseObject>.Data _d;
	
	
	ColChange(ListMetaType lmeta,
			AbstractPAList<? extends BaseObject>.Data d)
	{
		_lmeta = lmeta;
		_d = d;
	}		
	@Override
	public ColumnMeta getColMeta() {return _d.getColMeta();}
	@Override
	public ListMetaType getListMeta() {return _lmeta;}
	@Override
	public int[] getNdxs()
	{
		if (_idx == null)
		{
			_idx = _d.computeChanges();
			_size = _idx.length;
		}
		return _idx;
	}
	
	@Override
	public int[] getKeys()
	{
		return _d.getKeys(getNdxs());
	}
	
	@Override
	public int size() {return _size;}
	
	@Override
	public abstract String[] stringAccess();
	@Override
	public abstract float[] floatAccess();
	@Override
	public abstract int[] intAccess();
	@Override
	public abstract boolean[] booleanAccess();

	@Override
	public boolean equals(Object obj)
	{
		return getColMeta() == ((ColChange)obj).getColMeta();
	}
}

class BoolColChange extends ColChange
{
	AbstractPAList<? extends BaseObject>.BoolData _d;
	private boolean[] _vals;
	
	BoolColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.BoolData d)
	{
		super(lmeta, d);
		_d = d;
	}

	@Override
	public String[] stringAccess()
	{
		boolean[] ival = booleanAccess();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatAccess()
	{
		boolean[] ival = booleanAccess();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = ival[i] ? 1f : 0f;
		return rv;
	}

	@Override
	public int[] intAccess()
	{
		boolean[] val = booleanAccess();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = val[i] ? 1 : 0;
		return rv;
	}

	@Override
	public boolean[] booleanAccess()
	{
		if (_vals == null)
			_vals = _d.getBools(getNdxs());
		return _vals;
	}
	
}

class FloatColChange extends ColChange
{
	AbstractPAList<? extends BaseObject>.FloatData _d;
	private float[] _vals;
	
	FloatColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.FloatData d)
	{
		super(lmeta, d);
		_d = d;
	}

	@Override
	public String[] stringAccess()
	{
		float[] ival = floatAccess();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatAccess()
	{
		if (_vals == null)
			_vals = _d.getFloats(getNdxs());
		return _vals;
	}

	@Override
	public int[] intAccess()
	{
		float[] val = floatAccess();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Math.round(val[i]);
		return rv;
	}

	@Override
	public boolean[] booleanAccess()
	{
		float[] val = floatAccess();
		boolean[] rv = new boolean[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = val[i] != 0f;
		return rv;
	}

}

class IntColChange extends ColChange
{
	AbstractPAList<? extends BaseObject>.IntDataIfc _d;
	int[] _vals;
	
	IntColChange(ListMetaType lmeta, AbstractPAList<? extends BaseObject>.IntDataIfc d)
	{
		super(lmeta, d);
		_d = d;
	}

	@Override
	public String[] stringAccess()
	{
		int[] ival = intAccess();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatAccess()
	{
		int[] ival = intAccess();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = (float) ival[i];
		return rv;
	}

	@Override
	public int[] intAccess()
	{
		if (_vals == null)
			_vals = _d.getInts(getNdxs());
		return _vals;
	}

	@Override
	public boolean[] booleanAccess()
	{
		int[] ival = intAccess();
		boolean[] rv = new boolean[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = ival[i] != 0;
		return rv;
	}
}

class StringColChange extends ColChange
{
	AbstractPAList<? extends BaseObject>.StringData _d;
	private String[] _vals;

	StringColChange(ListMetaType lmeta,
			AbstractPAList<? extends BaseObject>.StringData d)
	{
		super(lmeta, d);
		_d = d;
	}

	@Override
	public String[] stringAccess()
	{
		if (_vals == null)
			_vals = _d.getStrings(getNdxs());
		return _vals;
	}

	@Override
	public float[] floatAccess()
	{
		String[] val = stringAccess();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Float.parseFloat(val[i]);
		return rv;
	}

	@Override
	public int[] intAccess()
	{
		String[] val = stringAccess();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Integer.parseInt(val[i]);
		return rv;
	}

	@Override
	public boolean[] booleanAccess()
	{
		String[] val = stringAccess();
		boolean[] rv = new boolean[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Boolean.parseBoolean(val[i]);
		return rv;
	}
	
}