package com.powerdata.openpa;

public class Load extends OneTermDev 
{
	LoadList _list;
	
	public Load(LoadList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** Get Max Load MW */
	public float getMaxP() throws PAModelException
	{
		return _list.getMaxP(_ndx);
	}
	
	/** Set Max Load MW */
	public void setMaxP(float mw) throws PAModelException
	{
		_list.setMaxP(_ndx, mw);
	}

	/** Get Max Load MVAr */
	public float getMaxQ() throws PAModelException
	{
		return _list.getMaxQ(_ndx);
	}

	/** Set Max Load MVAr */
	public void setMaxQ(float mvar) throws PAModelException
	{
		_list.setMaxQ(_ndx, mvar);
	}
}
