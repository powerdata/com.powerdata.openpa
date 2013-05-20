package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public class SeriesReactor extends BaseObject
{
	private SeriesReactorList<?> _list;  
	
	public SeriesReactor(int ndx, SeriesReactorList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getID() {return _list.getID(getIndex());}
	public int getFromNode() {return _list.getFromNode(getIndex());}
	public int getToNode() {return _list.getToNode(getIndex());}
	public float getR() {return _list.getR(getIndex());}
	public float getX() {return _list.getX(getIndex());}
	public void updateActvPower(float p) {_list.updateActvPower(getIndex(), p);}
	public void updateReacPower(float q) {_list.updateReacPower(getIndex(), q);}
}
