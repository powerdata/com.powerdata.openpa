package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;

public class PsseBaseObject extends AbstractBaseObject
{
	PsseBaseList<?> _plist;
	int _hashcode;
	
	public PsseBaseObject(PsseBaseList<?> list, int ndx)
	{
		super(list, ndx);
		_plist = list;
		_hashcode = list.getClass().hashCode() + getClass().hashCode() + ndx;
	}

	public PsseModel getPsseModel() {return _plist.getPsseModel();}
	@Override
	public int hashCode()
	{
		return _hashcode;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof PsseBaseObject)
		{
			PsseBaseObject o = (PsseBaseObject)obj;
			return _ndx == o._ndx && _plist.hashCode() == o._plist.hashCode();
		}
		return false;
	}
}
