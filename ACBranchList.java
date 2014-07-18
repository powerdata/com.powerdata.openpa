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
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
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
	public float getR(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getR(li.ofs);
	}

	@Override
	public void setR(int ndx, float r)
	{
		ListInfo li = getLI(ndx);
		li.list.setR(li.ofs, r);
	}

	@Override
	public float[] getR()
	{
		return getHelper(float[]::new, l -> l.getR());
	}

	@Override
	public void setR(float[] r)
	{
		setHelper(r, float[]::new, (l,v) -> l.setR(v));
	}

	@Override
	public float getX(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getX(li.ofs);
	}

	@Override
	public void setX(int ndx, float x)
	{
		ListInfo li = getLI(ndx);
		li.list.setX(li.ofs, x);
	}

	@Override
	public float[] getX()
	{
		return getHelper(float[]::new, l -> l.getX());
	}

	@Override
	public void setX(float[] x)
	{
		setHelper(x, float[]::new, (l,v) -> l.setX(v));
	}

	@Override
	public ACBranch get(int index)
	{
		return new ACBranch(this, index);
	}
}
