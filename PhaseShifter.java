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

	public void setControlMode(ControlMode m) throws PAModelException
	{
		_list.setControlMode(_ndx, m);
	}
	
	public boolean hasReg() throws PAModelException
	{
		return _list.hasReg(_ndx);
	}
	
	public void setReg(boolean v) throws PAModelException
	{
		_list.setReg(_ndx, v);
	}
	
	public float getMaxAng() throws PAModelException
	{
		return _list.getMaxAng(_ndx);
	}
	
	public void setMaxAng(float v) throws PAModelException
	{
		_list.setMaxAng(_ndx, v);
	}
	public float getMinAng() throws PAModelException
	{
		return _list.getMinAng(_ndx);
	}
	public void setMinAng(float v) throws PAModelException
	{
		_list.setMinAng(_ndx, v);
	}
	public float getRegMaxMW() throws PAModelException
	{
		return _list.getRegMaxMW(_ndx);
	}
	public void setRegMaxMW(float mw) throws PAModelException
	{
		_list.setRegMaxMW(_ndx, mw);
	}
	public float getRegMinMW() throws PAModelException
	{
		return _list.getRegMinMW(_ndx);
	}
	public void setRegMinMW(float mw) throws PAModelException
	{
		_list.setRegMinMW(_ndx, mw);
	}
	
}
