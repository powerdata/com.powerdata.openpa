package com.powerdata.openpa;

public abstract class ACBranchSubList<T extends ACBranch> extends TwoTermDevSubList<T> implements
		ACBranchList<T>
{

	public ACBranchSubList(ACBranchList<T> src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getR(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setR(int ndx, float r)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getR()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setR(float[] r)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getX(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX(int ndx, float x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getX()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setX(float[] x)
	{
		// TODO Auto-generated method stub
		
	}

}
