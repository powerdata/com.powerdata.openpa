package com.powerdata.openpa.psse;

public class Switch extends PsseBaseObject implements TwoTermDev
{
	protected SwitchList _list;
	
	public Switch(int ndx, SwitchList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "Switch "+getName();}

	@Override
	public Bus getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	@Override
	public Bus getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	@Deprecated // use getObjectName()
	public String getName() throws PsseModelException {return _list.getName(_ndx);}
	public SwitchState getState() throws PsseModelException {return _list.getState(_ndx);}
	public void setState(SwitchState state) throws PsseModelException { _list.setState(_ndx,state); }
	public boolean canOperateUnderLoad() throws PsseModelException {return _list.canOperateUnderLoad(_ndx); }

	@Override
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	@Override
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}

	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
	@Override
	public void setInSvc(boolean state) throws PsseModelException {_list.setInSvc(_ndx, state);}
}