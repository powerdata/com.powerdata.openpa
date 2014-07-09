package com.powerdata.openpa;

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
