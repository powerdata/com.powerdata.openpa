package com.powerdata.openpa;

public class Shunt extends OneTermDev
{
	ShuntList<? extends Shunt> _list;
	
	public Shunt(ShuntList<? extends Shunt> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public float getB()
	{
		return _list.getB(_ndx);
	}
	
	public void setB(float b)
	{
		_list.setB(_ndx, b);
	}
}
