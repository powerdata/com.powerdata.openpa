package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public class PhaseShifterWinding extends BaseObject
{
	private PhaseShftWndList<?> _list;
	
	public PhaseShifterWinding(int ndx, PhaseShftWndList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getObjectID() {return _list.getObjectID(getIndex());}
	public int getFromNode() {return _list.getFromNode(getIndex());}
	public int getToNode() {return _list.getToNode(getIndex());}
	public float getR() {return _list.getR(getIndex());}
	public float getX() {return _list.getX(getIndex());}
	public float getPhaseShift() {return _list.getPhaseShift(getIndex());}
	
	public void updateActvPower(float p) {_list.updateActvPower(getIndex(), p);}
	public void updateReacPower(float q) {_list.updateReacPower(getIndex(), q);}

}
