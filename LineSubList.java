package com.powerdata.openpa;

public class LineSubList extends ACBranchSubList<Line> implements LineList
{
	LineList _src;
	
	public LineSubList(LineList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getFromBchg(int ndx)
	{
		return _src.getFromBchg(_ndx[ndx]);
	}

	@Override
	public void setFromBchg(int ndx, float b)
	{
		_src.setFromBchg(_ndx[ndx], b);
	}

	@Override
	public float[] getFromBchg()
	{
		return mapFloat(_src.getFromBchg());
	}

	@Override
	public void setFromBchg(float[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setFromBchg(_ndx[i], b[i]);
	}

	@Override
	public float getToBchg(int ndx)
	{
		return _src.getToBchg(_ndx[ndx]);
	}

	@Override
	public void setToBchg(int ndx, float b)
	{
		_src.setToBchg(_ndx[ndx], b);
	}

	@Override
	public float[] getToBchg()
	{
		return mapFloat(_src.getToBchg());
	}

	@Override
	public void setToBchg(float[] b)
	{
		for(int i=0; i < _size; ++i)
			_src.setToBchg(_ndx[i], b[i]);
	}

	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}
	
}