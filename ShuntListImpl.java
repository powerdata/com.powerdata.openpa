package com.powerdata.openpa;

public abstract class ShuntListImpl<T extends Shunt> 
	extends OneTermDevListI<T> implements ShuntList<T>
{
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
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setB(int ndx, float b)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public float[] getB()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setB(float[] b)
	{
		// TODO Auto-generated method stub
		
	}
}
