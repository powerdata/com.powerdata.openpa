package com.powerdata.openpa;

public class LoadSubList extends OneTermDevSubList<Load> implements LoadList
{

	public LoadSubList(OneTermDevList<Load> src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getPL(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPL(int ndx, float pl)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getPL()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPL(float[] pl)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}
}
