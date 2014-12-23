package com.powerdata.openpa.tools;

import java.util.AbstractList;
import java.util.List;

public class ComplexSublist extends AbstractList<Complex>
{
	int[] _ndx;
	List<Complex> _src;
	
	public ComplexSublist(List<Complex> src, int[] ndx)
	{
		_src = src;
		_ndx = ndx;
	}

	@Override
	public Complex get(int index)
	{
		return _src.get(_ndx[index]);
	}

	@Override
	public int size()
	{
		return _ndx.length;
	}
}
