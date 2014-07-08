package com.powerdata.openpa;

public class Shunt extends OneTermDev
{
	ShuntList<? extends Shunt> _list;
	
	public Shunt(ShuntList<? extends Shunt> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get shunt admittance setpoint in MVAr @ unity bus voltage */
	public float getBS()
	{
		return _list.getBS(_ndx);
	}
	
	/** set shunt admittance setpoint in MVAr @ unity bus voltage */
	public void setBS(float b)
	{
		_list.setBS(_ndx, b);
	}
}
