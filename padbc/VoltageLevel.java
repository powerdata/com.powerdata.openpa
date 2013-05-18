package com.powerdata.openpa.padbc;

public abstract class VoltageLevel extends BaseObject implements Container
{
	private VoltageLevelList _list;
	
	public VoltageLevel(int ndx, VoltageLevelList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	public float getNominalKV() {return _list.getNominalKV(getIndex());}
}
