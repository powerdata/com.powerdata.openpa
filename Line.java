package com.powerdata.openpa;

public class Line extends ACBranch 
{
	LineList _list;

	public Line(LineList linelist, int ndx)
	{
		super(linelist, ndx);
		_list = linelist;
	}
	/** Branch charging susceptance entered in p.u. */
	public float getB()
	{
		float b = 0;
		return b;
	}

	/** get from-side charging susceptance */
	@Override
	public float getFromBchg()
	{
		return _list.getFromBchg(_ndx);
	}
	/** set from-side charging susceptance */
	@Override
	public void setFromBchg(float b)
	{
		_list.setFromBchg(_ndx, b);
	}
	/** get to-side charging susceptance */
	@Override
	public float getToBchg()
	{
		return _list.getToBchg(_ndx);
	}
	/** set to-side charging susceptance */
	@Override
	public void setToBchg(float b)
	{
		_list.setToBchg(_ndx, b);
	}

	public float getMVAPercent()	{	return 0;	}
}
