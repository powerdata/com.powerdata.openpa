package com.powerdata.openpa;

public class VoltageLevel extends Group 
{
	protected VoltageLevelList _list;
	
	public VoltageLevel(VoltageLevelList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

}
