package com.powerdata.openpa;

public class Shunt extends OneTermDev
{
	ShuntList _list;
	public Shunt(ShuntList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public float getB()	{	return 0;	}
}
