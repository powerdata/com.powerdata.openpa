package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class ZoneIn extends BaseObject
{
	protected ZoneInList _list;
	
	public ZoneIn(int ndx, ZoneInList list)
	{
		super(ndx);
		_list = list;
	}

	
	@Override
	public String getDebugName() throws PsseModelException {return getOWNAME();}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* raw PSS/e methods */
	
	/** Zone number */
	public int getI() {return _list.getI(_ndx);}
	/** Alphanumeric identifier assigned to zone */
	public String getOWNAME() {return _list.getZONAME(_ndx);}
	
}
