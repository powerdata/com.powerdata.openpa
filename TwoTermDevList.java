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

	@Override
	public float getFromP(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromP(li.ofs);
	}

	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromP(li.ofs, mw);
	}

	@Override
	public float[] getFromP() throws PAModelException
	{
		return getHelper(float[]::new, l -> getFromP());
	}

	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		setHelper(mw, float[]::new, (l,v) -> l.setFromP(v));
	}

	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromQ(li.ofs);
	}

	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromQ(li.ofs, mvar);
	}

	@Override
	public float[] getFromQ() throws PAModelException
	{
		return getHelper(float[]::new, l -> getFromQ());
	}

	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		setHelper(mvar, float[]::new, (l,v) -> l.setFromQ(v));
	}

	@Override
	public float getToP(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToP(li.ofs);
	}

	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToP(li.ofs, mw);
	}

	@Override
	public float[] getToP() throws PAModelException
	{
		return getHelper(float[]::new, l -> getToP());
	}

	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		setHelper(mw, float[]::new, (l,v) -> l.setToP(v));
	}

	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToQ(li.ofs);
	}

	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToQ(li.ofs, mvar);
	}

	@Override
	public float[] getToQ() throws PAModelException
	{
		return getHelper(float[]::new, l -> getToQ());
	}

	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		setHelper(mvar, float[]::new, (l,v) -> l.setToQ(v));
	}

}
