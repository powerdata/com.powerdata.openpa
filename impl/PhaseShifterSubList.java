package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.PhaseShifterList;

public class PhaseShifterSubList extends ACBranchSubListBase<PhaseShifter> implements PhaseShifterList
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

	@Override
	public boolean hasReg(int ndx) throws PAModelException
	{
		return _src.hasReg(_ndx[ndx]);
	}

	@Override
	public boolean[] hasReg() throws PAModelException
	{
		return mapBool(_src.hasReg());
	}

	@Override
	public void setReg(int ndx, boolean v) throws PAModelException
	{
		_src.setReg(_ndx[ndx], v);
	}

	@Override
	public void setReg(boolean[] v) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setReg(_ndx[i], v[i]);
	}

	@Override
	public float getMaxAng(int ndx) throws PAModelException
	{
		return _src.getMaxAng(_ndx[ndx]);
	}

	@Override
	public float[] getMaxAng() throws PAModelException
	{
		return mapFloat(_src.getMaxAng());
	}

	@Override
	public void setMaxAng(int ndx, float v) throws PAModelException
	{
		_src.setMaxAng(_ndx[ndx],v);
	}

	@Override
	public void setMaxAng(float[] v) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMaxAng(_ndx[i], v[i]);
	}

	@Override
	public float getMinAng(int ndx) throws PAModelException
	{
		return _src.getMinAng(_ndx[ndx]);
	}

	@Override
	public float[] getMinAng() throws PAModelException
	{
		return mapFloat(_src.getMinAng());
	}

	@Override
	public void setMinAng(int ndx, float v) throws PAModelException
	{
		_src.setMinAng(_ndx[ndx], v);
	}

	@Override
	public void setMinAng(float[] v) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setMinAng(_ndx[i], v[i]);
	}

	@Override
	public float getRegMaxMW(int ndx) throws PAModelException
	{
		return _src.getRegMaxMW(_ndx[ndx]);
	}

	@Override
	public float[] getRegMaxMW() throws PAModelException
	{
		return mapFloat(_src.getRegMaxMW());
	}

	@Override
	public void setRegMaxMW(int ndx, float mw) throws PAModelException
	{
		_src.setRegMaxMW(_ndx[ndx], mw);
	}

	@Override
	public void setRegMaxMW(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setRegMaxMW(_ndx[i], mw[i]);
	}

	@Override
	public float getRegMinMW(int ndx) throws PAModelException
	{
		return _src.getRegMinMW(_ndx[ndx]);
	}

	@Override
	public void setRegMinMW(int ndx, float mw) throws PAModelException
	{
		_src.setRegMinMW(_ndx[ndx], mw);
	}

	@Override
	public float[] getRegMinMW() throws PAModelException
	{
		return mapFloat(_src.getRegMinMW());
	}

	@Override
	public void setRegMinMW(float[] mw) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setRegMinMW(_ndx[i], mw[i]);
	}

}
