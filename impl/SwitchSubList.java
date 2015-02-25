package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.SwitchList;
import com.powerdata.openpa.Switch.State;

public class SwitchSubList extends TwoTermDevSubList<Switch> implements SwitchList
{
	SwitchList _src;
	
	public SwitchSubList(SwitchList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public State getState(int ndx) throws PAModelException
	{
		return _src.getState(_ndx[ndx]);
	}

	@Override
	public void setState(int ndx, State state) throws PAModelException
	{
		_src.setState(_ndx[ndx], state);
	}

	@Override
	public State[] getState() throws PAModelException
	{
		return mapObject(_src.getState());
	}

	@Override
	public void setState(State[] state) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setState(_ndx[i], state[i]);
	}

	@Override
	public boolean isOperableUnderLoad(int ndx) throws PAModelException
	{
		return _src.isOperableUnderLoad(_ndx[ndx]);
	}

	@Override
	public void setOperableUnderLoad(int ndx, boolean op) throws PAModelException
	{
		_src.setOperableUnderLoad(_ndx[ndx], op);
	}

	@Override
	public boolean[] isOperableUnderLoad() throws PAModelException
	{
		return mapBool(_src.isOperableUnderLoad());
	}

	@Override
	public void setOperableUnderLoad(boolean[] op) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOperableUnderLoad(_ndx[i], op[i]);
	}
	

	@Override
	public boolean isEnabled(int ndx) throws PAModelException
	{
		return _src.isEnabled(_ndx[ndx]);
	}

	@Override
	public void setEnabled(int ndx, boolean enable) throws PAModelException
	{
		_src.setEnabled(_ndx[ndx], enable);
	}

	@Override
	public boolean[] isEnabled() throws PAModelException
	{
		return mapBool(_src.isEnabled());
	}

	@Override
	public void setEnabled(boolean[] enable) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setEnabled(_ndx[i], enable[i]);
	}

	@Override
	public float getTransitTime(int ndx) throws PAModelException
	{
		return _src.getTransitTime(_ndx[ndx]);
	}

	@Override
	public float[] getTransitTime() throws PAModelException
	{
		return mapFloat(_src.getTransitTime());
	}

	@Override
	public void setTransitTime(int ndx, float t) throws PAModelException
	{
		_src.setTransitTime(_ndx[ndx], t);
	}

	@Override
	public void setTransitTime(float[] t) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setTransitTime(_ndx[i], t[i]);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Switch;
	}
}
