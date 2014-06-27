package com.powerdata.openpa;

public class Island extends AbstractGroupObject
{
	protected IslandList _list;
	
	public Island(IslandList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public boolean isEnergized()
	{
		return _list.isEnergized(_ndx);
	}

}
