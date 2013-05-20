package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public abstract class Area extends BaseObject implements Container
{
	private AreaList<?> _list;
	
	public Area(int ndx, AreaList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getObjectID() {return _list.getObjectID(getIndex());}
	public String getAreaName() {return _list.getAreaName(getIndex());}
}
