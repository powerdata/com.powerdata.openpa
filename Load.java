package com.powerdata.openpa;

import com.powerdata.openpa.psse.PsseModelException;

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
	/** get the cold load MW */
	public float getPcold()  throws PAModelException // {return _list.getPcold(_ndx);}
	{
		return 0;
	}
	/** get the load MW "setpoint" */
	public float getPS() throws PsseModelException // {return _list.getPS(_ndx);}
	{
		return 0;
	}
	/** set the load MW setpoint */
	public void setPS(float mw) throws PsseModelException// {_list.setPS(_ndx, mw);}
	{
		
	}

}
