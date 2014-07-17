package com.powerdata.openpa;

import com.powerdata.openpa.impl.AbstractBaseObject;

public class OneTermDev extends AbstractBaseObject
{
	OneTermDevList<? extends OneTermDev>	_list;

	public OneTermDev(OneTermDevList<? extends OneTermDev> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** Device terminal Bus */
	public Bus getBus()
	{
		return _list.getBus(_ndx);
	}
	
	/** Get device active power in MW */
	public float getP()
	{
		return _list.getP(_ndx);
	}
	
	/** Get device reactive power in MVAr */
	public float getQ()
	{
		return _list.getQ(_ndx);
	}
	
	/** Set device active power in MW */
	public void setP(float p)
	{
		_list.setP(_ndx, p);
	}
	
	/** Set device reactive power in MVAr */
	public void setQ(float q)
	{
		_list.setQ(_ndx, q);
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
