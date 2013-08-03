package com.powerdata.openpa.psse;

public class SwitchSubList extends SwitchList
{
	SwitchList _switches;
	int _ndxs[];
	public SwitchSubList(SwitchList switches, int ndxs[])
	{
		super(switches._model);
		_switches = switches;
		_ndxs = ndxs;
		_idToNdx = switches.idmap();
	}
	@Override
	public void commit() throws PsseModelException
	{
		_switches.commit();
	}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException
	{
		return _switches.getFromBus(_ndxs[ndx]);
	}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException
	{
		return _switches.getToBus(_ndxs[ndx]);
	}
	@Override
	public String getName(int ndx) throws PsseModelException
	{
		return _switches.getName(_ndxs[ndx]);
	}
	@Override
	public SwitchState getState(int ndx) throws PsseModelException
	{
		return _switches.getState(_ndxs[ndx]);
	}
	@Override
	public void setState(int ndx, SwitchState state) throws PsseModelException
	{
		_switches.setState(_ndxs[ndx],state);
	}
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return _switches.getObjectID(_ndxs[ndx]);
	}
	@Override
	public int size() { return _ndxs.length; }
	@Override
	public boolean canOperateUnderLoad(int ndx) throws PsseModelException
	{
		return _switches.canOperateUnderLoad(_ndxs[ndx]);
	}
	@Override
	public Switch get(String id) {return new Switch(_ndxs[_idToNdx.get(id)], this);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _switches.getObjectName(_ndxs[ndx]);}
	
}
