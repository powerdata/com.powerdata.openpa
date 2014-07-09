package com.powerdata.openpa;

public class SVCListImpl extends ShuntListImpl<SVC> implements SVCList
{
	BusList _buses;

	float[][] _minb=IFlt(),_maxb=IFlt(),_vs=IFlt();
	boolean[][] _isreg=IBool();
	int[][] _rbus = IInt();

	public SVCListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
		_buses = model.getBuses();
	}
	public SVCListImpl(PAModel model, int size)
	{
		super(model, size);
		_buses = model.getBuses();
	}

	protected SVCListImpl() {super();}

	@Override
	public SVC get(int index)
	{
		return new SVC(this, index);
	}
	@Override
	public float getMinB(int ndx)
	{
		return getFloat(_minb, ndx);
	}
	@Override
	public void setMinB(int ndx, float b)
	{
		setFloat(_minb, ndx, b);
	}
	@Override
	public float[] getMinB()
	{
		return getFloat(_minb);
	}
	@Override
	public void setMinB(float[] b)
	{
		setFloat(_minb, b);
	}
	@Override
	public float getMaxB(int ndx)
	{
		return getFloat(_maxb, ndx);
	}
	@Override
	public void setMaxB(int ndx, float b)
	{
		setFloat(_maxb, ndx, b);
	}
	@Override
	public float[] getMaxB()
	{
		return getFloat(_maxb);
	}
	@Override
	public void setMaxB(float[] b)
	{
		setFloat(_maxb, b);
	}
	@Override
	public boolean isRegKV(int ndx)
	{
		return getBool(_isreg, ndx);
	}
	@Override
	public void setRegKV(int ndx, boolean reg)
	{
		setBool(_isreg, ndx, reg);
	}
	@Override
	public boolean[] isRegKV()
	{
		return getBool(_isreg);
	}
	@Override
	public void setRegKV(boolean[] reg)
	{
		setBool(_isreg, reg);
	}
	@Override
	public float getVS(int ndx)
	{
		return getFloat(_vs, ndx);
	}
	@Override
	public void setVS(int ndx, float kv)
	{
		setFloat(_vs, ndx, kv);
	}
	@Override
	public float[] getVS()
	{
		return getFloat(_vs);
	}
	@Override
	public void setVS(float[] kv)
	{
		setFloat(_vs, kv);
	}
	@Override
	public Bus getRegBus(int ndx)
	{
		return _buses.get(getInt(_rbus, ndx));
	}
	@Override
	public void setRegBus(int ndx, Bus b)
	{
		setInt(_rbus, ndx, b.getIndex());
	}
	@Override
	public Bus[] getRegBus()
	{
		return _buses.toArray(getInt(_rbus));
	}
	@Override
	public void setRegBus(Bus[] b)
	{
		setInt(_rbus, BaseList.ObjectNdx(b));
	}

}
