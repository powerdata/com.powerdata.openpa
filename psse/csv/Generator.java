package com.powerdata.openpa.psse.csv;

public class Generator extends com.powerdata.openpa.psse.Generator
{
	private GeneratorList _list;
	public Generator(int ndx, GeneratorList list)
	{
		super(ndx, list);
		_list = list;
	}
	public String getName() { return _list.getName(_ndx); }
}
