package com.powerdata.openpa;

import com.powerdata.openpa.impl.SuperListMgr;


public class ACBranchList extends SuperListMgr<ACBranch, ACBranchListIfc<ACBranch>> implements
		ACBranchListIfc<ACBranch>
{

	public ACBranchList(PALists lists) throws PAModelException
	{
		super(lists, ACBranchListIfc.class);
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
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
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
	public float getR(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getR(li.ofs);
	}

	@Override
	public void setR(int ndx, float r) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setR(li.ofs, r);
	}

	@Override
	public float[] getR() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getR());
	}

	@Override
	public void setR(float[] r) throws PAModelException
	{
		setHelper(r, float[]::new, (l,v) -> l.setR(v));
	}

	@Override
	public float getX(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getX(li.ofs);
	}

	@Override
	public void setX(int ndx, float x) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setX(li.ofs, x);
	}

	@Override
	public float[] getX() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getX());
	}

	@Override
	public void setX(float[] x) throws PAModelException
	{
		setHelper(x, float[]::new, (l,v) -> l.setX(v));
	}

	@Override
	public ACBranch get(int index)
	{
		return new ACBranch(this, index);
	}
}
