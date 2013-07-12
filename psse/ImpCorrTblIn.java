package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
//TODO:  Implement class
public class ImpCorrTblIn extends BaseObject
{
	protected ImpCorrTblInList _list;
	
	public ImpCorrTblIn(int ndx, ImpCorrTblInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
}
