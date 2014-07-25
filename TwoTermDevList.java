package com.powerdata.openpa;

import com.powerdata.openpa.impl.SuperListMgr;


public class TwoTermDevList extends SuperListMgr<TwoTermDev,TwoTermDevListIfc<TwoTermDev>> implements
		TwoTermDevListIfc<TwoTermDev>
{

	public TwoTermDevList(PALists lists) throws PAModelException
	{
		super(lists, TwoTermDevListIfc.class);
	}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromBus(li.ofs);
	}

	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromBus(li.ofs, b);
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return getHelper(Bus[]::new, l -> l.getFromBus());
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setFromBus(v));
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToBus(li.ofs);
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToBus(li.ofs, b);
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return getHelper(Bus[]::new, l -> l.getToBus());
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.isOutOfSvc(li.ofs);
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setOutOfSvc(li.ofs, state);
	}

	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return getHelper(boolean[]::new, l -> l.isOutOfSvc());
	}

	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		setHelper(state, boolean[]::new, (l,v) -> l.setOutOfSvc(v));
	}

	@Override
	public TwoTermDev get(int index)
	{
		return new TwoTermDev(this, index);
	}

}
