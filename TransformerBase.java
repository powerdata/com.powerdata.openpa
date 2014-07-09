package com.powerdata.openpa;

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
	public float getGmag()
	{
		return _list.getGmag(_ndx);
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	@Override
	public void setGmag(float g)
	{
		_list.setGmag(_ndx, g);
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public float getBmag()
	{
		return _list.getBmag(_ndx);
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public void setBmag(float b)
	{
		_list.setBmag(_ndx, b);
	}

	/** get phase shift setpoint through branch in Degrees */
	@Override
	public float getShift()
	{
		return _list.getShift(_ndx);
	}
	
	/** set phase shift setpoint through branch in Degrees */
	@Override
	public void setShift(float sdeg)
	{
		_list.setShift(_ndx, sdeg);
	}
}
