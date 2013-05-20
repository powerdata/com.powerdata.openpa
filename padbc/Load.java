package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

public class Load extends BaseObject
{
	private LoadList<?> _list;
	
	public Load(int ndx, LoadList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	public float getActvPwr() {return _list.getActvPwr(getIndex());}
	public float getReacPwr() {return _list.getReacPwr(getIndex());}

	/* methods to use if we want external-world units */
//	public float getMW() {return _list.getMW(getIndex());}
//	public float getMVAr() {return _list.getMVAr(getIndex());}

	public void updateActvPwr(float p) {_list.updateActvPwr(getIndex(), p);}
	public void updateReacPwr(float q) {_list.updateReacPwr(getIndex(), q);}

//	public void updateMW(float p) {_list.updateMW(getIndex(), p);}
//	public void updateMVAr(float q) {_list.updateMVAr(getIndex(), q);}
}
