package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Shunt extends BaseObject implements OneTermDev
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

}
