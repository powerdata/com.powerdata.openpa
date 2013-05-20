package com.powerdata.openpa.padbc.incsys;

import com.powerdata.openpa.padbc.Generator;

public class CsvGenerator extends Generator
{
	private CsvGeneratorList _list;
	public CsvGenerator(int ndx, CsvGeneratorList list)
	{
		super(ndx, list);
		_list = list;
	}
	public String getName() { return _list.getName(_ndx); }
	@Override
	public String toString()
	{
		return String.format("[%d] %s Name: %s Node: %d",_ndx,getID(),getName(),getNode());
	}
}
