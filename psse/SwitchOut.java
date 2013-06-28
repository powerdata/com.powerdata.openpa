package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SwitchOut extends BaseObject
{
	protected SwitchOutList _list;
	
	public SwitchOut(int ndx, SwitchOutList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	public void setState(SwitchState newstate) {_list.setSwitchState(_ndx, newstate);}
	
}
