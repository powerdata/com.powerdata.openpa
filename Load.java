package com.powerdata.openpa;

public class Load extends OneTermDev 
{
	LoadList _list;
	
	public Load(LoadList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** Get Load MW */
	public float getP()
	{
		return _list.getP(_ndx);
	}
	
	/** Set Load MW */
	public void setP(float mw)
	{
		_list.setP(_ndx, mw);
	}
	
	/** Get Load MVAr */
	public float getQ()
	{
		return _list.getQ(_ndx);
	}
	
	/** Get Load MVAr */
	public void setQ(float mvar)
	{
		_list.setQ(_ndx, mvar);
	}
	
	/** Get Max Load MW */
	public float getMaxP()
	{
		return _list.getMaxP(_ndx);
	}
	
	/** Set Max Load MW */
	public void setMaxP(float mw)
	{
		_list.setMaxP(_ndx, mw);
	}

	/** Get Max Load MVAr */
	public float getMaxQ()
	{
		return _list.getMaxQ(_ndx);
	}

	/** Set Max Load MVAr */
	public void setMaxQ(float mvar)
	{
		_list.setMaxQ(_ndx, mvar);
	}
}
