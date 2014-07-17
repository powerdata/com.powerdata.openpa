package com.powerdata.openpa.impl;

import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.PhaseShifterList;

public class PhaseShifterSubList extends TransformerBaseSubList<PhaseShifter> implements PhaseShifterList
{
	PhaseShifterList _src;
	
	public PhaseShifterSubList(PhaseShifterList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public PhaseShifter get(int index)
	{
		return new PhaseShifter(this, index);
	}

}
