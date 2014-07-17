package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevList;

public class AllTwoTermDevList extends SuperListMgr<TwoTermDev,TwoTermDevList<TwoTermDev>> implements
		TwoTermDevList<TwoTermDev>
{

	public AllTwoTermDevList(PALists lists)
			throws ReflectiveOperationException, RuntimeException
	{
		super(lists, TwoTermDevList.class);
	}

	@Override
	public Bus getFromBus(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromBus(li.ofs);
	}

	@Override
	public void setFromBus(int ndx, Bus b)
	{
		ListInfo li = getLI(ndx);
		li.list.setFromBus(li.ofs, b);
	}

	@Override
	public Bus[] getFromBus()
	{
		return getHelper(Bus[]::new, l -> l.getFromBus());
	}

	@Override
	public void setFromBus(Bus[] b)
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setFromBus(v));
	}

	@Override
	public Bus getToBus(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getToBus(li.ofs);
	}

	@Override
	public void setToBus(int ndx, Bus b)
	{
		ListInfo li = getLI(ndx);
		li.list.setToBus(li.ofs, b);
	}

	@Override
	public Bus[] getToBus()
	{
		return getHelper(Bus[]::new, l -> l.getToBus());
	}

	@Override
	public void setToBus(Bus[] b)
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.isInSvc(li.ofs);
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		ListInfo li = getLI(ndx);
		li.list.setInSvc(li.ofs, state);
	}

	@Override
	public boolean[] isInSvc()
	{
		return getHelper(boolean[]::new, l -> l.isInSvc());
	}

	@Override
	public void setInSvc(boolean[] state)
	{
		setHelper(state, boolean[]::new, (l,v) -> l.setInSvc(v));
	}

	@Override
	public TwoTermDev get(int index)
	{
		return new TwoTermDev(this, index);
	}

}
