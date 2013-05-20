package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Ownership extends BaseObject
{
	protected OwnershipList<?> _list;

	public Ownership(int ndx, OwnershipList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* Convenience Methods */

	public Owner getOwner() {return _list.getOwner(_ndx);}
	
	/* raw psse methods */
	
	/** Owner number */
	public int getO() {return _list.getO(_ndx);}
	/** Fraction of total ownership assigned to owner */
	public float getF() {return _list.getF(_ndx);}
}
