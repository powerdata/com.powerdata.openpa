package com.powerdata.openpa;

public class OutOfService extends AbstractBaseObject
{
	OutOfServiceList<? extends OutOfService> _list;
	
	public OutOfService(OutOfServiceList<? extends OutOfService> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** is device in service */
	public boolean isOutOfSvc() throws PAModelException
	{
		return _list.isOutOfSvc(_ndx);
	}
	
	/** set device in/out of service */
	public void setOutOfSvc(boolean state) throws PAModelException
	{
		_list.setOutOfSvc(_ndx, state);
	}

}
