package com.powerdata.openpa.psse;
//TODO:  Implement class
public class ImpCorrTbl extends PsseBaseObject
{
	protected ImpCorrTblList _list;
	
	public ImpCorrTbl(int ndx, ImpCorrTblList list)
	{
		super(list,ndx);
		_list = list;
	}
}
