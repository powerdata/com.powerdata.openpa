package com.powerdata.openpa;

public class IsolationGroup extends AbstractGroupObject
{
	protected IsolationGroupList _list;
	
	public IsolationGroup(IsolationGroupList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public SwitchList getOperableSwitches()
	{
		return _list.getOperableSwitches(_ndx);
	}
	
	public SwitchList getInoperableSwitches()
	{
		return _list.getInoperableSwitches(_ndx);
	}
	
}
