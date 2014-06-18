package com.powerdata.openpa;

public class Line extends ACBranch 
{
	LineList _linelist;

	public Line(LineList linelist, int ndx)
	{
		super(linelist, ndx);
		_linelist = linelist;
	}
	/** Branch charging susceptance entered in p.u. */
	public float getB()
	{
		float b = 0;
		return b;
	}

	public float getMVAPercent()	{	return 0;	}
}
