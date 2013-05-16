package com.powerdata.openpa.padbc;

public class Generator extends BaseObject
{
	private GeneratorList _list;
	
	public Generator(int ndx, GeneratorList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	

}
