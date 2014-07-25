package com.powerdata.openpa;

import com.powerdata.openpa.impl.AbstractBaseObject;

public class TwoTermDev extends AbstractBaseObject
{
	TwoTermDevListIfc<? extends TwoTermDev> _list;
	
	public TwoTermDev(TwoTermDevListIfc<? extends TwoTermDev> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get from-side Bus */
	public Bus getFromBus() throws PAModelException
	{
		return _list.getFromBus(_ndx);
	}
	
	/** get to-side bus */
	public Bus getToBus() throws PAModelException
	{
		return _list.getToBus(_ndx);
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
