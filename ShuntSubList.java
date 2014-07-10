package com.powerdata.openpa;

public abstract class ShuntSubList<T extends Shunt> extends OneTermDevSubList<T> implements ShuntList<T>
{
	ShuntList<T> _src;
	
	public ShuntSubList(ShuntList<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getB(int ndx)
	{
		return _src.getB(_ndx[ndx]);
	}

	@Override
	public void setB(int ndx, float b)
	{
		_src.setB(_ndx[ndx], b);
	}

	@Override
	public float[] getB()
	{
		return mapFloat(_src.getB());
	}

	@Override
	public void setB(float[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setB(_ndx[i], b[i]);
	}

}
