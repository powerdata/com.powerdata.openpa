package com.powerdata.openpa;

public abstract class ShuntSubList<T extends Shunt> extends OneTermDevSubList<T> implements ShuntList<T>
{

	public ShuntSubList(OneTermDevList<T> src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

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
