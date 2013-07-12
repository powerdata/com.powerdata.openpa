package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public class BusOut extends BaseObject
{
	protected BusOutList _list;
	
	public BusOut(int ndx, BusOutList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	
	public void setVmag(float vm) {_list.setVmag(_ndx, vm);}
	public void setVang(float va) {_list.setVang(_ndx, va);}
	public void setVoltage(Complex v)  {_list.setVoltage(_ndx, v);}
}
