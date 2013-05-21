package com.powerdata.openpa.psse.csv;

public class Bus extends com.powerdata.openpa.psse.Bus
{
	private BusList _list;
	public Bus(int ndx, BusList list)
	{
		super(ndx, list);
		_list = list;
	}
}
