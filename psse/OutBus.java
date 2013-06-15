package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public class OutBus extends BaseObject
{
	protected OutBusList _list;
	
	public OutBus(int ndx, OutBusList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	public void updateVmag(float vm) {_list.updateVmag(_ndx, vm);}
	public void updateVang(float va) {_list.updateVang(_ndx, va);}
	public void updateVoltage(Complex v)  {_list.updateVoltagte(_ndx, v);}
}
