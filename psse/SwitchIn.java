package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SwitchIn extends BaseObject
{
	protected SwitchInList _list;
	
	public SwitchIn(int ndx, SwitchInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "Switch "+getName();}


	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	public BusIn getBus() {return _list.getBus(_ndx);}
	public String getName() {return _list.getName(_ndx);}
	public SwitchState getState() {return _list.getState(_ndx);}
	
}
