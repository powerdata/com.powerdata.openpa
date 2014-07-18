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
	public Bus getFromBus()
	{
		return _list.getFromBus(_ndx);
	}
	
	/** get to-side bus */
	public Bus getToBus()
	{
		return _list.getToBus(_ndx);
	}
	
	/** is device in service */
	public boolean isInSvc()
	{
		return _list.isInSvc(_ndx);
	}
	
	/** set device in/out of service */
	public void setInSvc(boolean state)
	{
		_list.setInSvc(_ndx, state);
	}
}
