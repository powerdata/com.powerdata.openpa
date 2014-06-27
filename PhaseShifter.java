package com.powerdata.openpa;

public class PhaseShifter extends TransformerBase
{
	PhaseShifterList _list;

	public PhaseShifter(PhaseShifterList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get phase shift through branch in Degrees */
	@Override
	public float getShift()
	{
		return _list.getShift(_ndx);
	}
	/** set phase shift through branch in Degrees */
	@Override
	public void setShift(float sdeg)
	{
		_list.setShift(_ndx, sdeg);
	}
}
