package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public abstract class VoltageLevel extends BaseObject implements Container
{
	private VoltageLevelList<?> _list;
	
	public VoltageLevel(int ndx, VoltageLevelList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getObjectID() {return _list.getObjectID(getIndex());}
	public float getNominalKV() {return _list.getNominalKV(getIndex());}
}
