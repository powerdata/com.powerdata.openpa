package com.powerdata.openpa.psse;

public class SVC extends PsseBaseObject implements OneTermDev
{

	protected SvcList	_list;

	public SVC(int ndx, SvcList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}

}
