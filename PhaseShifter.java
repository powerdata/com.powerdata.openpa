package com.powerdata.openpa;

public class PhaseShifter extends ACBranch
{
	PhaseShifterList _list;
	
	public enum ControlMode
	{
		FixedMW, FixedAngle
	}

	public PhaseShifter(PhaseShifterList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public ControlMode getControlMode() throws PAModelException
	{
		return _list.getControlMode(_ndx);
	}

	void setControlMode(ControlMode m) throws PAModelException
	{
		_list.setControlMode(_ndx, m);
	}
}
