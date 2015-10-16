package com.powerdata.openpa;

import com.powerdata.openpa.TwoTermBaseList.TwoTermBase;


public class TwoTermDev extends InService implements TwoTermBase
{
	TwoTermDevListIfc<? extends TwoTermDev> _list;
	
//	public enum Side {From,To};
	
	public TwoTermDev(TwoTermDevListIfc<? extends TwoTermDev> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	@Override
	public Bus getFromBus() throws PAModelException
	{
		return _list.getFromBus(_ndx);
	}

	@Override
	public Bus getToBus() throws PAModelException
	{
		return _list.getToBus(_ndx);
	}

	/** from-side active power (MW) */
	public float getFromP() throws PAModelException
	{
		return _list.getFromP(_ndx);
	}
	
	/** from-side active power (MW) */
	public void setFromP(float mw) throws PAModelException
	{
		_list.setFromP(_ndx, mw);
	}
	
	/** from-side reactive power (MVAr) */
	public float getFromQ() throws PAModelException
	{
		return _list.getFromQ(_ndx);
	}

	/** from-side reactive power (MVAr) */
	public void setFromQ(float mvar) throws PAModelException
	{
		_list.setFromQ(_ndx, mvar);
	}

	/** to-side active power (MW) */
	public float getToP() throws PAModelException
	{
		return _list.getToP(_ndx);
	}
	
	/** to-side active power (MW) */
	public void setToP(float mw) throws PAModelException
	{
		_list.setToP(_ndx, mw);
	}
	
	/** to-side reactive power (MVAr) */
	public float getToQ() throws PAModelException
	{
		return _list.getToQ(_ndx);
	}

	/** to-side reactive power (MVAr) */
	public void setToQ(float mvar) throws PAModelException
	{
		_list.setToQ(_ndx, mvar);
	}

}
