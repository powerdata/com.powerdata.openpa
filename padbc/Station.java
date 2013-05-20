package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public abstract class Station extends BaseObject implements Container
{
	private StationList<?> _list;
	
	public Station(int ndx, StationList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	public String getStationName() {return _list.getStationName(getIndex());}
}
