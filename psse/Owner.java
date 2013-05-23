package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Owner extends BaseObject
{
	protected OwnerList<?> _list;
	
	public Owner(int ndx, OwnerList<?> list)
	{
		super(ndx);
		_list = list;
	}


	@Override
	public String getDebugName() throws PsseModelException {return getOWNAME();}
	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* raw PSS/e methods */
	
	/** Owner number */
	public int getI() {return _list.getI(_ndx);}
	/** Alphanumeric identifier assigned to owner */
	public String getOWNAME() {return _list.getOWNAME(_ndx);}
	
}
