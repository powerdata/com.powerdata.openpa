package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class OwnershipIn extends BaseObject
{
	protected OwnershipInList _list;

	public OwnershipIn(int ndx, OwnershipInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}

	/* Convenience Methods */

	public OwnerIn getOwner() throws PsseModelException {return _list.getOwner(_ndx);}
	
	/* raw psse methods */
	
	/** Owner number 
	 * @throws PsseModelException */
	public int getO() throws PsseModelException {return _list.getO(_ndx);}
	/** Fraction of total ownership assigned to owner 
	 * @throws PsseModelException */
	public float getF() throws PsseModelException {return _list.getF(_ndx);}
}
