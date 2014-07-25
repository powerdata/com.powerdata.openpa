package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.PhaseShifterList;

public class PhaseShifterSubList extends TransformerBaseSubList<PhaseShifter> implements PhaseShifterList
{
	PhaseShifterList _src;
	
	public PhaseShifterSubList(PhaseShifterList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public PhaseShifter get(int index)
	{
		return new PhaseShifter(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.PhaseShifter;
	}

	@Override
	public ControlMode getControlMode(int ndx) throws PAModelException
	{
		return _src.getControlMode(_ndx[ndx]);
	}

	@Override
	public ControlMode[] getControlMode() throws PAModelException
	{
		return mapObject(_src.getControlMode());
	}

	@Override
	public void setControlMode(int ndx, ControlMode m) throws PAModelException
	{
		_src.setControlMode(_ndx[ndx], m);
	}

	@Override
	public void setControlMode(ControlMode[] m) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setControlMode(_ndx[i], m[i]);
	}

}
