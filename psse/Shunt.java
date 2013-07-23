package com.powerdata.openpa.psse;

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
	public float getNomB() throws PsseModelException {return _list.getNomB(_ndx);}

	/** is Switched on */
	public boolean isSwitchedOn() throws PsseModelException {return _list.isSwitchedOn(_ndx);}

	/** get connected bus */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB() throws PsseModelException {return _list.getB(_ndx);}
}
