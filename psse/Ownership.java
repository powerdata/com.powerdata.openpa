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
	public String getID() {return _list.getID(_ndx);}

	public int getO() {return _list.getO(_ndx);}
	public float getF() {return _list.getF(_ndx);}
}
