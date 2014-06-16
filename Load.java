package com.powerdata.openpa;

public class Load extends OneTermDev 
{
	LoadList _list;
	
	public Load(LoadList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get active component of constant MVA load in MW */
	public float getPL() throws PAModelException
	{
		return _list.getPL(_ndx);
	}
	
}
