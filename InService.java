package com.powerdata.openpa;

public class InService extends AbstractBaseObject
{
	InServiceList<? extends InService> _list;
	
	public InService(InServiceList<? extends InService> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** is device in service */
	public boolean isInService() throws PAModelException
	{
		return _list.isInService(_ndx);
	}
	
	/** set device in/out of service */
	public void setInService(boolean state) throws PAModelException
	{
		_list.setInService(_ndx, state);
	}

}
