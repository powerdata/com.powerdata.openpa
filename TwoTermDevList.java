package com.powerdata.openpa;

import java.util.AbstractList;
import java.util.Set;

public class TwoTermDevList extends AbstractList<TwoTermDev> implements
		TwoTermDevListIfc<TwoTermDev>
{
	TwoTermDevListIfc<? extends TwoTermDev> _src;

	public TwoTermDevList(TwoTermDevListIfc<? extends TwoTermDev> src)
	{
		_src = src;
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
	public TwoTermDev getByKey(int key)
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
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _src.getFromBus(ndx);
	}

	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		_src.setFromBus(ndx, b);
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return _src.getFromBus();
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		_src.setFromBus(b);
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _src.getToBus(ndx);
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		_src.setToBus(ndx, b);
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return _src.getToBus();
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		_src.setToBus(b);
	}

	@Override
	public boolean isInService(int ndx) throws PAModelException
	{
		return _src.isInService(ndx);
	}

	@Override
	public void setInService(int ndx, boolean state) throws PAModelException
	{
		_src.setInService(ndx, state);
	}

	@Override
	public boolean[] isInService() throws PAModelException
	{
		return _src.isInService();
	}

	@Override
	public void setInService(boolean[] state) throws PAModelException
	{
		_src.setInService(state);
	}

	@Override
	public float getFromP(int ndx) throws PAModelException
	{
		return _src.getFromP(ndx);
	}

	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		_src.setFromP(ndx, mw);
	}

	@Override
	public float[] getFromP() throws PAModelException
	{
		return _src.getFromP();
	}

	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		_src.setFromP(mw);
	}

	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		return _src.getFromQ(ndx);
	}

	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		_src.setFromQ(ndx, mvar);
	}

	@Override
	public float[] getFromQ() throws PAModelException
	{
		return _src.getFromQ();
	}

	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		_src.setFromQ(mvar);
	}

	@Override
	public float getToP(int ndx) throws PAModelException
	{
		return _src.getToP(ndx);
	}

	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		_src.setToP(ndx, mw);
	}

	@Override
	public float[] getToP() throws PAModelException
	{
		return _src.getToP();
	}

	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		_src.setToP(mw);
	}

	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		return _src.getToQ(ndx);
	}

	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		_src.setToQ(ndx, mvar);
	}

	@Override
	public float[] getToQ() throws PAModelException
	{
		return _src.getToQ();
	}

	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		_src.setToQ(mvar);
	}

	@Override
	public TwoTermDev get(int index)
	{
		return _src.get(index);
	}

	@Override
	public int size()
	{
		return _src.size();
	}

	@Override
	public TwoTermDev getByID(String id) throws PAModelException
	{
		return _src.getByID(id);
	}
	@Override
	public boolean objEquals(int ndx, Object obj)
	{
		return _src.objEquals(ndx, obj);
	}

	@Override
	public int objHash(int ndx)
	{
		return _src.objHash(ndx);
	}

	@Override
	public Set<ColumnMeta> getColTypes()
	{
		return _src.getColTypes();
	}

}
