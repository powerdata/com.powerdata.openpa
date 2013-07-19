package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Zone extends BaseObject
{
	protected ZoneList _list;
	
	public Zone(int ndx, ZoneList list)
	{
		super(list,ndx);
		_list = list;
	}

	
	@Override
	public String getDebugName() throws PsseModelException {return getOWNAME();}

	/* raw PSS/e methods */
	
	/** Zone number */ 
	public int getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Alphanumeric identifier assigned to zone */
	public String getOWNAME() throws PsseModelException {return _list.getZONAME(_ndx);}
	
}
