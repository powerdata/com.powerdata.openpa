package com.powerdata.openpa;

public class LoadListImpl extends OneTermDevListI<Load> implements LoadList 
{
	public static final LoadList Empty = new LoadListImpl();
	
	public LoadListImpl() {super();}

	public LoadListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public LoadListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
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
