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
	public float getBS(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setBS(int ndx, float b)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public float[] getBS()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setBS(float[] b)
	{
		// TODO Auto-generated method stub
		
	}
}
