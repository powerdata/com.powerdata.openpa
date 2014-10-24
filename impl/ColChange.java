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
	public int size() { getNdxs(); return _size;}
	
	@Override
	public abstract String[] stringValues();
	@Override
	public abstract float[] floatValues();
	@Override
	public abstract int[] intValues();
	@Override
	public abstract boolean[] booleanValues();

	@Override
	public boolean equals(Object obj)
	{
		Object s = getColMeta();
		if (obj instanceof ColumnMeta)
		{
			return s == obj;
		}
		return s == ((ColChange)obj).getColMeta();
	}
	
	@Override
	public void clear()
	{
		_d.clear();
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
	public String[] stringValues()
	{
		boolean[] ival = booleanValues();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatValues()
	{
		boolean[] ival = booleanValues();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = ival[i] ? 1f : 0f;
		return rv;
	}

	@Override
	public int[] intValues()
	{
		boolean[] val = booleanValues();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = val[i] ? 1 : 0;
		return rv;
	}

	@Override
	public boolean[] booleanValues()
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
	public String[] stringValues()
	{
		float[] ival = floatValues();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatValues()
	{
		if (_vals == null)
			_vals = _d.getFloats(getNdxs());
		return _vals;
	}

	@Override
	public int[] intValues()
	{
		float[] val = floatValues();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Math.round(val[i]);
		return rv;
	}

	@Override
	public boolean[] booleanValues()
	{
		float[] val = floatValues();
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
	public String[] stringValues()
	{
		int[] ival = intValues();
		String[] rv = new String[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = String.valueOf(ival[i]);
		return rv;
	}

	@Override
	public float[] floatValues()
	{
		int[] ival = intValues();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = (float) ival[i];
		return rv;
	}

	@Override
	public int[] intValues()
	{
		if (_vals == null)
			_vals = _d.getInts(getNdxs());
		return _vals;
	}

	@Override
	public boolean[] booleanValues()
	{
		int[] ival = intValues();
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
	public String[] stringValues()
	{
		if (_vals == null)
			_vals = _d.getStrings(getNdxs());
		return _vals;
	}

	@Override
	public float[] floatValues()
	{
		String[] val = stringValues();
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Float.parseFloat(val[i]);
		return rv;
	}

	@Override
	public int[] intValues()
	{
		String[] val = stringValues();
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Integer.parseInt(val[i]);
		return rv;
	}

	@Override
	public boolean[] booleanValues()
	{
		String[] val = stringValues();
		boolean[] rv = new boolean[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = Boolean.parseBoolean(val[i]);
		return rv;
	}
	
}