package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Ownership extends BaseObject
{
	protected OwnershipList _list;

	public Ownership(int ndx, OwnershipList list)
	{
		super(list,ndx);
		_list = list;
	}

	/* Convenience Methods */

	public Owner getOwner() throws PsseModelException {return _list.getOwner(_ndx);}
	
	/* raw psse methods */
	
	/** Owner number 
	 * @throws PsseModelException */
	public int getO() throws PsseModelException {return _list.getO(_ndx);}
	/** Fraction of total ownership assigned to owner 
	 * @throws PsseModelException */
	public float getF() throws PsseModelException {return _list.getF(_ndx);}
}
