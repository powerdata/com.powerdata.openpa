package com.powerdata.openpa.padbc;

public abstract class BaseObject
{
	protected int _ndx;
	
	public BaseObject(int ndx)
	{
		_ndx = ndx;
	}

	public int getIndex() {return _ndx;}
	public abstract String getID();
}
