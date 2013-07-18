package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SVC extends BaseObject implements OneTermDev
{

	protected SvcList	_list;

	public SVC(int ndx, SvcList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}

}
