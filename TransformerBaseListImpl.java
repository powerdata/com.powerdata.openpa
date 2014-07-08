package com.powerdata.openpa;

public abstract class TransformerBaseListImpl<T extends TransformerBase>
		extends ACBranchListImpl<T> implements TransformerBaseList<T>
{
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
	public float getFromTap(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getFromTap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFromTap(float[] a)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getToTap(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getToTap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setToTap(float[] a)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getGmag(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGmag(int ndx, float g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getGmag()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGmag(float[] g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBmag(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBmag(int ndx, float b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getBmag()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBmag(float[] b)
	{
		// TODO Auto-generated method stub
		
	}
}
