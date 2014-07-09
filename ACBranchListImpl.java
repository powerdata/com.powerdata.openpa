package com.powerdata.openpa;

public abstract class ACBranchListImpl<T extends ACBranch> extends TwoTermDevListI<T> implements ACBranchList<T>
{
	protected ACBranchListImpl(){super();}
	
	float[][] _r=IFlt(), _x=IFlt();
	
	protected ACBranchListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	protected ACBranchListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public float getR(int ndx)
	{
		return getFloat(_r, ndx);
	}

	@Override
	public void setR(int ndx, float r)
	{
		setFloat(_r, ndx, r);
	}

	@Override
	public float[] getR()
	{
		return getFloat(_r);
	}

	@Override
	public void setR(float[] r)
	{
		setFloat(_r, r);
	}

	@Override
	public float getX(int ndx)
	{
		return getFloat(_x, ndx);
	}

	@Override
	public void setX(int ndx, float x)
	{
		setFloat(_x, ndx, x);
	}

	@Override
	public float[] getX()
	{
		return getFloat(_x);
	}

	@Override
	public void setX(float[] x)
	{
		setFloat(_x, x);
	}

}
