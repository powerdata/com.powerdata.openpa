package com.powerdata.openpa;

public class ShuntListImpl<T extends Shunt> 
	extends OneTermDevListI<T> implements ShuntList<T>
{
	float[][] _b = IFlt();
	
	public ShuntListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public ShuntListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public ShuntListImpl() {super();}
	
	@Override
	public float getB(int ndx)
	{
		return getFloat(_b, ndx);
	}
	@Override
	public void setB(int ndx, float b)
	{
		setFloat(_b, ndx, b);
	}
	@Override
	public float[] getB()
	{
		return getFloat(_b);
	}
	@Override
	public void setB(float[] b)
	{
		setFloat(_b, b);
	}
	
}
