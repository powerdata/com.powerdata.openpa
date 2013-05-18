package com.powerdata.openpa.padbc;

public abstract class Area extends BaseObject implements Container
{
	private AreaList _list;
	
	public Area(int ndx, AreaList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	public String getAreaName() {return _list.getAreaName(getIndex());}
}
