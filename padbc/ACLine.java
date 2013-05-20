package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public class ACLine extends BaseObject
{
	private ACLineList<?> _list;
	
	public ACLine(int ndx, ACLineList<?> list)
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
	public float getFromBChg() {return _list.getFromBChg(getIndex());}
	public float getToBChg() {return _list.getToBChg(getIndex());}
	public void updateActvPower(float p) {_list.updateActvPower(getIndex(), p);}
	public void updateReacPower(float q) {_list.updateReacPower(getIndex(), q);}

}
