package com.powerdata.openpa;

public abstract class TransformerBaseListImpl<T extends TransformerBase>
		extends ACBranchListImpl<T> implements TransformerBaseList<T>
{
	float[][] _b = IFlt(), _g = IFlt(), _deg=IFlt();
	float[][] _ft = IFlt(), _tt = IFlt();
	

	protected TransformerBaseListImpl()
	{
		super();
	}

	protected TransformerBaseListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}

	protected TransformerBaseListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public float getGmag(int ndx)
	{
		return getFloat(_g, ndx);
	}

	@Override
	public void setGmag(int ndx, float g)
	{
		setFloat(_g, ndx, g);
	}

	@Override
	public float[] getGmag()
	{
		return getFloat(_g);
	}

	@Override
	public void setGmag(float[] g)
	{
		setFloat(_g, g);
	}

	@Override
	public float getBmag(int ndx)
	{
		return getFloat(_b, ndx);
	}

	@Override
	public void setBmag(int ndx, float b)
	{
		setFloat(_b, ndx, b);
	}

	@Override
	public float[] getBmag()
	{
		return getFloat(_b);
	}

	@Override
	public void setBmag(float[] b)
	{
		setFloat(_b, b);
	}

	@Override
	public float getShift(int ndx)
	{
		return getFloat(_deg, ndx);
	}

	@Override
	public void setShift(int ndx, float sdeg)
	{
		setFloat(_deg, ndx, sdeg);
	}

	@Override
	public float[] getShift()
	{
		return getFloat(_deg);
	}

	@Override
	public void setShift(float[] sdeg)
	{
		setFloat(_deg, sdeg);
	}
	@Override
	public float getFromTap(int ndx)
	{
		return getFloat(_ft, ndx);
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		setFloat(_ft, ndx, a);
	}

	@Override
	public float[] getFromTap()
	{
		return getFloat(_ft);
	}

	@Override
	public void setFromTap(float[] a)
	{
		setFloat(_ft, a);
	}

	@Override
	public float getToTap(int ndx)
	{
		return getFloat(_tt, ndx);
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		setFloat(_tt, ndx, a);
	}

	@Override
	public float[] getToTap()
	{
		return getFloat(_tt);
	}

	@Override
	public void setToTap(float[] a)
	{
		setFloat(_tt, a);
	}

}
