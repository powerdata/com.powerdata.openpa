package com.powerdata.openpa;

public class VoltageLevel extends Group 
{
	protected VoltageLevelList _list;
	
	public VoltageLevel(VoltageLevelList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public float getBaseKV() throws PAModelException
	{
		return _list.getBaseKV(_ndx);
	}
	
	public void setBaseKV(float k) throws PAModelException
	{
		_list.setBaseKV(_ndx, k);
	}
	
}
