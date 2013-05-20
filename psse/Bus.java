package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Bus extends BaseObject
{
	protected BusList<?> _list;
	
	public Bus(int ndx, BusList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getID() {return _list.getID(_ndx);}
	public int getI() {return _list.getI(_ndx);}
	public String getNAME() {return _list.getNAME(_ndx);}
	public float getBASKV() {return _list.getBASKV(_ndx);}
	public int getIDE() {return _list.getIDE(_ndx);}
	public int getGL() {return _list.getGL(_ndx);}
	public int getBL() {return _list.getBL(_ndx);}
	public int getAREA() {return _list.getAREA(_ndx);}
	public int getZONE() {return _list.getZONE(_ndx);}
	public float getVM() {return _list.getVM(_ndx);}
	public float getVA() {return _list.getVA(_ndx);}
	public int getOWNER() {return _list.getOWNER(_ndx);}
	
}
