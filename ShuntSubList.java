package com.powerdata.openpa;

public abstract class ShuntSubList<T extends Shunt> extends OneTermDevSubList<T> implements ShuntList<T>
{

	public ShuntSubList(OneTermDevList<T> src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

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
