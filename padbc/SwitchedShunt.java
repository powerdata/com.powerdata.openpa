package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public class SwitchedShunt extends BaseObject
{
	private SwitchedShuntList<?> _list;
	
	public SwitchedShunt(int ndx, SwitchedShuntList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	
	public float getNominalB() {return _list.getNominalB(getIndex());}
	// public float getNominalMVAr() {return _list.getNominalMVAr(getIndex()));}
	
	public void updateReacPwr(float b) {_list.updateReacPwr(getIndex(), b);}
	// public void updateMVAr(float mvar) {_list.updateMVAr(getIndex(), mvar);}
}
