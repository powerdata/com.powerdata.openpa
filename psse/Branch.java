package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Branch extends BaseObject
{
	protected BranchList _list;
	
	public Branch(int ndx, BranchList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	public Bus getFrBus() {return _list.getFrBus(_ndx);}
	public Bus getToBus() {return _list.getToBus(_ndx);}
	public float getR() {return _list.getR(_ndx);}
	public float getX() {return _list.getX(_ndx);}
	public float getFrB() {return _list.getFrB(_ndx);}
	public float getToB() {return _list.getToB(_ndx);}
	public float getFrTapRatio() {return _list.getFrTapRatio(_ndx);}
	public float getToTapRatio() {return _list.getToTapRatio(_ndx);}
	public float getPhaseShift() {return _list.getPhaseShift(_ndx);}

}
