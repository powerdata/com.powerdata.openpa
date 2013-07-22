package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class PsseBaseObject extends BaseObject
{
	PsseBaseList<?> _plist;
	
	public PsseBaseObject(PsseBaseList<?> list, int ndx)
	{
		super(list, ndx);
		_plist = list;
	}

	public PsseModel getPsseModel() {return _plist.getPsseModel();}
}
