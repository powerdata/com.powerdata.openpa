package com.powerdata.openpa;

import java.util.AbstractList;

public class FixedShuntList extends AbstractList<FixedShunt> implements FixedShuntListIfc<FixedShunt>
{

	FixedShuntListIfc<? extends FixedShunt> _src;

	public FixedShuntList(FixedShuntListIfc<? extends FixedShunt> src)
	{
		_src = src;
	}
	
	
	@Override
	public Bus getBus(int ndx) throws PAModelException
	{
		return _src.getBus(ndx);
	}

	@Override
	public void setBus(int ndx, Bus b) throws PAModelException
	{
		_src.setBus(ndx, b);
	}

	@Override
	public Bus[] getBus() throws PAModelException
	{
		return _src.getBus();
	}

	@Override
	public void setBus(Bus[] b) throws PAModelException
	{
		_src.setBus(b);
	}

	@Override
	public float getP(int ndx) throws PAModelException
	{
		return _src.getP(ndx);
	}

	@Override
	public void setP(int ndx, float p) throws PAModelException
	{
		_src.setP(ndx, p);
	}
	

	@Override
	public float[] getP() throws PAModelException
	{
		return _src.getP();
	}

	@Override
	public void setP(float[] p) throws PAModelException
	{
		_src.setP(p);
	}

	@Override
	public float getQ(int ndx) throws PAModelException
	{
		return _src.getQ(ndx);
	}

	@Override
	public void setQ(int ndx, float q) throws PAModelException
	{
		_src.setQ(ndx, q);
	}

	@Override
	public float[] getQ() throws PAModelException
	{
		return _src.getQ();
	}

	@Override
	public void setQ(float[] q) throws PAModelException
	{
		_src.setQ(q);
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		return _src.isOutOfSvc(ndx);
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		_src.setOutOfSvc(ndx, state);
	}

	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return _src.isOutOfSvc();
	}

	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		_src.setOutOfSvc(state);
	}

	@Override
	public int getKey(int ndx)
	{
		return _src.getKey(ndx);
	}

	@Override
	public int[] getKeys()
	{
		return _src.getKeys();
	}

	@Override
	public String getID(int ndx) throws PAModelException
	{
		return _src.getID(ndx);
	}

	@Override
	public String[] getID() throws PAModelException
	{
		return _src.getID();
	}

	@Override
	public void setID(String[] id) throws PAModelException
	{
		_src.setID(id);
	}

	@Override
	public void setID(int ndx, String id) throws PAModelException
	{
		_src.setID(ndx, id);
	}

	@Override
	public String getName(int ndx) throws PAModelException
	{
		return _src.getName(ndx);
	}

	@Override
	public void setName(int ndx, String name) throws PAModelException
	{
		_src.setName(ndx, name);
	}

	@Override
	public String[] getName() throws PAModelException
	{
		return _src.getName();
	}

	@Override
	public void setName(String[] name) throws PAModelException
	{
		_src.setName(name);
	}

	@Override
	public FixedShunt getByKey(int key)
	{
		return _src.getByKey(key);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return _src.getListMeta();
	}

	@Override
	public int getIndex(int ndx)
	{
		return _src.getIndex(ndx);
	}

	@Override
	public float getB(int ndx) throws PAModelException
	{
		return _src.getB(ndx);
	}

	@Override
	public void setB(int ndx, float b) throws PAModelException
	{
		_src.setB(ndx, b);
	}

	@Override
	public float[] getB() throws PAModelException
	{
		return _src.getB();
	}

	@Override
	public void setB(float[] b) throws PAModelException
	{
		_src.setB(b);
	}

	@Override
	public FixedShunt get(int index)
	{
		return _src.get(index);
	}

	@Override
	public int size()
	{
		return _src.size();
	}


	@Override
	public FixedShunt getByID(String id) throws PAModelException
	{
		return _src.getByID(id);
	}
}
