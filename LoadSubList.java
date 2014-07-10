package com.powerdata.openpa;

public class LoadSubList extends OneTermDevSubList<Load> implements LoadList
{
	LoadList _src;
	
	public LoadSubList(LoadList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}

	@Override
	public float getMaxP(int ndx)
	{
		return _src.getMaxP(_ndx[ndx]);
	}

	@Override
	public void setMaxP(int ndx, float mw)
	{
		_src.setMaxP(_ndx[ndx], mw);
	}

	@Override
	public float[] getMaxP()
	{
		return mapFloat(_src.getMaxP());
	}

	@Override
	public void setMaxP(float[] mw)
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxP(_ndx[i], mw[i]);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return _src.getMaxQ(_ndx[ndx]);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		_src.setMaxQ(_ndx[ndx], mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return mapFloat(_src.getMaxQ());
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxQ(_ndx[i], mvar[i]);
	}
}
