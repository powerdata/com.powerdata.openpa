package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Branch extends BaseObject
{
	protected BranchList _list;
	
	public Branch(int ndx, BranchList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	public Bus getFromBus() {return _list.getFromBus(_ndx);}
	public Bus getToBus() {return _list.getToBus(_ndx);}
	public float getR() {return _list.getR(_ndx);}
	

}
