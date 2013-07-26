package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public class Shunt extends PsseBaseObject implements OneTermDev
{

	protected ShuntList	_list;

	public Shunt(int ndx, ShuntList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}

	/** shunt nominal susceptance, p.u. at nominal bus kv */
	public Complex getCaseY() throws PsseModelException {return _list.getCaseY(_ndx);}

	/** is Switched on */
	public boolean isSwitchedOn() throws PsseModelException {return _list.isSwitchedOn(_ndx);}

	/** get connected bus */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB() throws PsseModelException {return _list.getB(_ndx);}

	@Override
	public Complex getRTS() throws PsseModelException {return _list.getRTS(_ndx);}
	@Override
	public void setRTS(Complex s) {_list.setRTS(_ndx, s);}
}
