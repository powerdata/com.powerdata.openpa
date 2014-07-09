package com.powerdata.openpa;

public class TransformerListImpl extends TransformerBaseListImpl<Transformer> implements TransformerList
{
	float[][] _ft = IFlt(), _tt = IFlt();
	
	public static final TransformerList	Empty	= new TransformerListImpl();

	public TransformerListImpl() {super();}
	
	public TransformerListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public TransformerListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public Transformer get(int index)
	{
		return new Transformer(this, index);
	}

	@Override
	public float getFromTap(int ndx)
	{
		return getFloat(_ft, ndx);
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		setFloat(_ft, ndx, a);
	}

	@Override
	public float[] getFromTap()
	{
		return getFloat(_ft);
	}

	@Override
	public void setFromTap(float[] a)
	{
		setFloat(_ft, a);
	}

	@Override
	public float getToTap(int ndx)
	{
		return getFloat(_tt, ndx);
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		setFloat(_tt, ndx, a);
	}

	@Override
	public float[] getToTap()
	{
		return getFloat(_tt);
	}

	@Override
	public void setToTap(float[] a)
	{
		setFloat(_tt, a);
	}

}
