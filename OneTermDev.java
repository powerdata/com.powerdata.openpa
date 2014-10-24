package com.powerdata.openpa;


public class OneTermDev extends OutOfService
{
	OneTermDevListIfc<? extends OneTermDev>	_list;

	public OneTermDev(OneTermDevListIfc<? extends OneTermDev> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** Device terminal Bus */
	public Bus getBus() throws PAModelException
	{
		return _list.getBus(_ndx);
	}
	
	/** Get device active power in MW */
	public float getP() throws PAModelException
	{
		return _list.getP(_ndx);
	}
	
	/** Get device reactive power in MVAr */
	public float getQ() throws PAModelException
	{
		return _list.getQ(_ndx);
	}
	
	/** Set device active power in MW */
	public void setP(float p) throws PAModelException
	{
		_list.setP(_ndx, p);
	}
	
	/** Set device reactive power in MVAr */
	public void setQ(float q) throws PAModelException
	{
		_list.setQ(_ndx, q);
	}
	
}
