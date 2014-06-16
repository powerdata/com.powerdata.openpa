package com.powerdata.openpa;

public class Line extends ACBranch 
{
	LineList _linelist;

	public Line(LineList linelist, int ndx)
	{
		super(linelist, ndx);
		_linelist = linelist;
	}

}
