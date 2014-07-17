package com.powerdata.openpa;

class AllOneTermDevList extends
		SuperListMgr<OneTermDev, OneTermDevList<OneTermDev>> implements
		OneTermDevList<OneTermDev>
{
	public AllOneTermDevList(PALists lists)
			throws ReflectiveOperationException, RuntimeException
	{
		super(lists, OneTermDevList.class);
	}
	@Override
	public Bus getBus(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getBus(li.ofs);
	}
	@Override
	public void setBus(int ndx, Bus b)
	{
		ListInfo li = getLI(ndx);
		li.list.setBus(li.ofs, b);
	}
	@Override
	public Bus[] getBus()
	{
		return getHelper(Bus[]::new, l -> l.getBus());
	}
	@Override
	public void setBus(Bus[] b)
	{
		setHelper(b, Bus[]::new, (l, v) -> l.setBus(v));
	}
	@Override
	public float getP(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getP(li.ofs);
	}
	@Override
	public void setP(int ndx, float p)
	{
		ListInfo li = getLI(ndx);
		li.list.setP(li.ofs, p);
	}
	@Override
	public float[] getP()
	{
		return getHelper(float[]::new, l -> l.getP());
	}
	@Override
	public void setP(float[] p)
	{
		setHelper(p, float[]::new, (l,v) -> l.setP(v));
	}
	@Override
	public float getQ(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getQ(li.ofs);
	}
	@Override
	public void setQ(int ndx, float q)
	{
		ListInfo li = getLI(ndx);
		li.list.setQ(li.ofs, q);
	}
	@Override
	public float[] getQ()
	{
		return getHelper(float[]::new, l -> l.getQ());
	}
	@Override
	public void setQ(float[] q)
	{
		setHelper(q, float[]::new, (l, v) -> l.setQ(v));
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
	public OneTermDev get(int index)
	{
		return new OneTermDev(this, index);
	}
}