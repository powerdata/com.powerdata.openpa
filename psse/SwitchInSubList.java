package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.StringAttrib;

public class SwitchInSubList extends SwitchInList
{
	SwitchInList _switches;
	int _ndxs[];
	public SwitchInSubList(SwitchInList switches, int ndxs[])
	{
		super(switches._model);
		_switches = switches;
		_ndxs = ndxs;
	}
	@Override
	public BusIn getBus1(int ndx) throws PsseModelException
	{
		return _switches.getBus1(_ndxs[ndx]);
	}
	@Override
	public BusIn getBus2(int ndx) throws PsseModelException
	{
		return _switches.getBus2(_ndxs[ndx]);
	}
	@Override
	public String getName(int ndx) throws PsseModelException
	{
		return _switches.getName(_ndxs[ndx]);
	}
	@Override
	public SwitchState getState(int ndx) throws PsseModelException
	{
		return _switches.getState(_ndxs[ndx]);
	}
	@Override
	public void setState(int ndx, SwitchState state) throws PsseModelException
	{
		_switches.setState(_ndxs[ndx],state);
	}
	@Override
	public String getObjectID(int ndx)
	{
		return _switches.getObjectID(_ndxs[ndx]);
	}
	@Override
	public StringAttrib<SwitchIn> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<SwitchIn> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<SwitchIn> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<SwitchIn> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public int size() { return _ndxs.length; }
}
