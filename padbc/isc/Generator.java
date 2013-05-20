package com.powerdata.openpa.padbc.isc;

public class Generator extends com.powerdata.openpa.padbc.Generator
{
	private GeneratorList _list;
	public Generator(int ndx, GeneratorList list)
	{
		super(ndx, list);
		_list = list;
	}
	public String getName() { return _list.getName(_ndx); }
	@Override
	public String toString()
	{
		return String.format("[%d] %s Name: %s Node: %d",_ndx,getObjectID(),getName(),getNode());
	}
}
