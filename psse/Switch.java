package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Switch extends BaseObject implements TwoTermDev
{
	protected SwitchList _list;
	
	public Switch(int ndx, SwitchList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "Switch "+getName();}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}

	@Override
	public Bus getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	@Override
	public Bus getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	public String getName() throws PsseModelException {return _list.getName(_ndx);}
	public SwitchState getState() throws PsseModelException {return _list.getState(_ndx);}
	public void setState(SwitchState state) throws PsseModelException { _list.setState(_ndx,state); }
}
