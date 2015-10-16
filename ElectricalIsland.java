package com.powerdata.openpa;

public class ElectricalIsland extends Group
{
	protected ElectricalIslandList _list;
	
	public ElectricalIsland(ElectricalIslandList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public boolean isEnergized() throws PAModelException
	{
		return _list.isEnergized(_ndx);
	}
	
	public float getFreq() throws PAModelException
	{
		return _list.getFreq(_ndx);
	}
	
	public void setFreq(float f) throws PAModelException
	{
		_list.setFreq(_ndx, f);
	}
	
}
