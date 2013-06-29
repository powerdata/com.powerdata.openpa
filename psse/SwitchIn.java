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

	public BusIn getBus1() {return _list.getBus1(_ndx);}
	public BusIn getBus2() {return _list.getBus2(_ndx);}
	public String getName() {return _list.getName(_ndx);}
	public SwitchState getState() {return _list.getState(_ndx);}
	
}
