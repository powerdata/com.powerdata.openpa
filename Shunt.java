package com.powerdata.openpa;

public class Shunt extends OneTermDev
{
	ShuntList<? extends Shunt> _list;
	
	public Shunt(ShuntList<? extends Shunt> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get shunt admittance in MVAr @ unity bus voltage */
	public float getB()
	{
		return _list.getB(_ndx);
	}
	
	/** set shunt admittance in MVAr @ unity bus voltage */
	public void setB(float b)
	{
		_list.setB(_ndx, b);
	}
}
