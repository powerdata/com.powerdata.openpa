package com.powerdata.openpa;

public class TransformerSubList extends TransformerBaseSubList<Transformer> implements TransformerList
{
	TransformerList _src;
	
	public TransformerSubList(TransformerList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Transformer get(int index)
	{
		return new Transformer(this, index);
	}

	@Override
	public float getFromTap(int ndx)
	{
		return _src.getFromTap(_ndx[ndx]);
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		_src.setFromTap(_ndx[ndx], a);
	}

	@Override
	public float[] getFromTap()
	{
		return mapFloat(_src.getFromTap());
	}

	@Override
	public void setFromTap(float[] a)
	{
		for(int i=0; i < _size; ++i)
			_src.setFromTap(_ndx[i], a[i]);
	}

	@Override
	public float getToTap(int ndx)
	{
		return _src.getToTap(_ndx[ndx]);
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		_src.setToTap(_ndx[ndx], a);
	}

	@Override
	public float[] getToTap()
	{
		return mapFloat(_src.getToTap());
	}

	@Override
	public void setToTap(float[] a)
	{
		for(int i=0; i < _size; ++i)
			_src.setToTap(_ndx[i], a[i]);
	}
}
