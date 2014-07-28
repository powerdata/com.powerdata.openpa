package com.powerdata.openpa;

import com.powerdata.openpa.impl.SuperListMgr;

public class FixedShuntList extends SuperListMgr<FixedShunt,FixedShuntListIfc<FixedShunt>> implements FixedShuntListIfc<FixedShunt>
{

	public FixedShuntList(PALists lists)
			throws PAModelException
	{
		super(lists, FixedShuntListIfc.class);
	}

	@Override
	public Bus getBus(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getBus(li.ofs);
	}

	@Override
	public void setBus(int ndx, Bus b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setBus(li.ofs, b);
	}

	@Override
	public Bus[] getBus() throws PAModelException
	{
		return getHelper(Bus[]::new, l -> l.getBus());
	}

	@Override
	public void setBus(Bus[] b) throws PAModelException
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setBus(v));
	}

	@Override
	public float getP(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getP(li.ofs);
	}

	@Override
	public void setP(int ndx, float p) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setP(li.ofs, p);
	}

	@Override
	public float[] getP() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getP());
	}

	@Override
	public void setP(float[] p) throws PAModelException
	{
		setHelper(p, float[]::new, (l,v) -> l.setP(v));
	}

	@Override
	public float getQ(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getQ(li.ofs);
	}

	@Override
	public void setQ(int ndx, float q) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setQ(li.ofs, q);
	}

	@Override
	public float[] getQ() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getQ());
	}
	

	@Override
	public void setQ(float[] q) throws PAModelException
	{
		setHelper(q, float[]::new, (l,v) -> l.setQ(v));
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
	public float getB(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getB(li.ofs);
	}

	@Override
	public void setB(int ndx, float b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setB(li.ofs, b);
	}

	@Override
	public float[] getB() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getB());
	}

	@Override
	public void setB(float[] b) throws PAModelException
	{
		setHelper(b, float[]::new, (l,v) -> l.setB(v));
	}

	@Override
	public FixedShunt get(int index)
	{
		return new FixedShunt(this, index);
	}
}
