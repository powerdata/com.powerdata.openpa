package com.powerdata.openpa.impl;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TransformerBaseList;

public class TransformerBase extends ACBranch
{
	TransformerBaseList<? extends TransformerBase> _list;
	
	public TransformerBase(TransformerBaseList<? extends TransformerBase> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	@Override
	public float getGmag() throws PAModelException
	{
		return _list.getGmag(_ndx);
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	@Override
	public void setGmag(float g) throws PAModelException
	{
		_list.setGmag(_ndx, g);
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public float getBmag() throws PAModelException
	{
		return _list.getBmag(_ndx);
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public void setBmag(float b) throws PAModelException
	{
		_list.setBmag(_ndx, b);
	}

	/** get phase shift setpoint through branch in Degrees */
	@Override
	public float getShift() throws PAModelException
	{
		return _list.getShift(_ndx);
	}
	
	/** set phase shift setpoint through branch in Degrees */
	@Override
	public void setShift(float sdeg) throws PAModelException
	{
		_list.setShift(_ndx, sdeg);
	}
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	@Override
	public float getFromTap() throws PAModelException
	{
		return _list.getFromTap(_ndx);
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	@Override
	public void setFromTap(float a) throws PAModelException
	{
		_list.setFromTap(_ndx, a);
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	@Override
	public float getToTap() throws PAModelException
	{
		return _list.getToTap(_ndx);
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	@Override
	public void setToTap(float a) throws PAModelException
	{
		_list.setToTap(_ndx, a);
	}
}
