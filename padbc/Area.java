package com.powerdata.openpa.padbc;

public abstract class Area extends BaseObject implements Container
{
	private AreaList _list;
	
	public Area(int ndx, AreaList list)
	{
		super(ndx);
		_list = list;
	}
}
