package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
//TODO:  Implement class
public class ImpCorrTbl extends BaseObject
{
	protected ImpCorrTblList _list;
	
	public ImpCorrTbl(int ndx, ImpCorrTblList list)
	{
		super(list,ndx);
		_list = list;
	}
}
