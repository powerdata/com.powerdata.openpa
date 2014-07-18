package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
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
	public State getState(int ndx)
	{
		return _src.getState(_ndx[ndx]);
	}

	@Override
	public void setState(int ndx, State state)
	{
		_src.setState(_ndx[ndx], state);
	}

	@Override
	public State[] getState()
	{
		return mapObject(_src.getState());
	}

	@Override
	public void setState(State[] state)
	{
		for(int i=0; i < _size; ++i)
			_src.setState(_ndx[i], state[i]);
	}

	@Override
	public boolean isOperableUnderLoad(int ndx)
	{
		return _src.isOperableUnderLoad(_ndx[ndx]);
	}

	@Override
	public void setOperableUnderLoad(int ndx, boolean op)
	{
		_src.setOperableUnderLoad(_ndx[ndx], op);
	}

	@Override
	public boolean[] isOperableUnderLoad()
	{
		return mapBool(_src.isOperableUnderLoad());
	}

	@Override
	public void setOperableUnderLoad(boolean[] op)
	{
		for(int i=0; i < _size; ++i)
			_src.setOperableUnderLoad(_ndx[i], op[i]);
	}
	

	@Override
	public boolean isEnabled(int ndx)
	{
		return _src.isEnabled(_ndx[ndx]);
	}

	@Override
	public void setEnabled(int ndx, boolean enable)
	{
		_src.setEnabled(_ndx[ndx], enable);
	}

	@Override
	public boolean[] isEnabled()
	{
		return mapBool(_src.isEnabled());
	}

	@Override
	public void setEnabled(boolean[] enable)
	{
		for(int i=0; i < _size; ++i)
			_src.setEnabled(_ndx[i], enable[i]);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.Switch;
	}
}
