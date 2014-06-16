package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

public class SwitchSubList extends SwitchList
{
	int[] _ndx;
	SwitchList _src;

	protected SwitchSubList(PALists model, SwitchList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx), src.getFBusKeys(srcndx),
			src.getTBusKeys(srcndx));
		_ndx = srcndx;
		_src = src;
	}

	int map(int sndx) {return _ndx[sndx];}
	
	@Override
	public State getState(int ndx)
	{
		return _src.getState(map(ndx));
	}

	@Override
	public void setState(int ndx, State state)
	{
		_src.setState(map(ndx), state);
	}

	@Override
	public State[] getState()
	{
		int n = size();
		State[] rv = new State[n];
		for(int i=0; i < n; ++i)
			rv[i] = _src.getState(map(i));
		return rv;
	}

	@Override
	public void setState(State[] state)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			_src.setState(map(i), state[i]);
	}

	@Override
	public Bus getFromBus(int ndx)
	{
		return _buses.get(_fbx[ndx]);
	}

	@Override
	public BusList getFromBuses()
	{
		return _src.getSubListFromBuses(_ndx);
	}

	@Override
	public Bus getToBus(int ndx)
	{
		return _buses.get(_tbx[ndx]);
	}

	@Override
	public BusList getToBuses()
	{
		return _src.getSubListToBuses(_ndx);
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		return _src.isInSvc(map(ndx));
	}

	@Override
	public boolean[] isInSvc()
	{
		return _src.getSubListInSvc(_ndx);
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		_src.setInSvc(map(ndx), state);
	}

	@Override
	public void setInSvc(boolean[] state)
	{
		_src.setSubListInSvc(state, _ndx);
	}

	@Override
	public String getID(int ndx)
	{
		return _src.getID(map(ndx));
	}

	@Override
	public void setID(int ndx, String id)
	{
		_src.setID(map(ndx), id);
	}

	@Override
	public String[] getID()
	{
		return _src.getSubListIDs(_ndx);
	}

	@Override
	public void setID(String[] id)
	{
		_src.setSubListIDs(id, _ndx);
	}

	@Override
	public String getName(int ndx)
	{
		return _src.getName(map(ndx));
	}

	@Override
	public void setName(int ndx, String name)
	{
		_src.setName(map(ndx), name);
	}

	@Override
	public String[] getName()
	{
		return _src.getSubListNames(_ndx);
	}

	@Override
	public void setName(String[] name)
	{
		_src.setSubListNames(name, _ndx);
	}

	
	
}
