package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
//TODO:  Implement class
public abstract class ImpCorrTbl extends BaseObject
{
	protected ImpCorrTblList<?> _list;
	
	public ImpCorrTbl(int ndx, ImpCorrTblList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	

}
