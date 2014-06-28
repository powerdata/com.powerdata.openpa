package com.powerdata.openpa;

public class Area extends Group
{
	protected AreaList _list;
	public Area(AreaList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
}
