package com.powerdata.openpa.padbc;

public class ACLine extends Branch
{
	private ACLineList _list;
	
	public ACLine(int ndx, ACLineList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	@Override
	public int getFromNode() {return _list.getFromNode(getIndex());}
	@Override
	public int getToNode() {return _list.getToNode(getIndex());}
	@Override
	public float getR() {return _list.getR(getIndex());}
	@Override
	public float getX() {return _list.getX(getIndex());}
	public float getFromBChg() {return _list.getFromBChg(getIndex());}
	public float getToBChg() {return _list.getToBChg(getIndex());}
	public void updateActvPower(float p) {_list.updateActvPower(getIndex(), p);}
	public void updateReacPower(float q) {_list.updateReacPower(getIndex(), q);}

}
