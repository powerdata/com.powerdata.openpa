package com.powerdata.openpa.padbc;

public abstract class PowerAppsDBObject
{
	private int _ndx;
	
	public PowerAppsDBObject(int ndx)
	{
		_ndx = ndx;
	}

	public int getIndex() {return _ndx;}
	public abstract String getID();
}
