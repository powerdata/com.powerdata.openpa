package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Zone extends BaseObject
{
	protected ZoneList<?> _list;
	
	public Zone(int ndx, ZoneList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* raw PSS/e methods */
	
	/** Zone number */
	public int getI() {return _list.getI(_ndx);}
	/** Alphanumeric identifier assigned to zone */
	public String getOWNAME() {return _list.getZONAME(_ndx);}
	
}
