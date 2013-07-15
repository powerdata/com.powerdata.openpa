package com.powerdata.openpa.psse.conversions;

public abstract class XfrZToolFactory
{
	public static XfrZToolFactory Open(int psseversion)
	{
		XfrZToolFactory f = null;
		if (psseversion <= 30)
		{
			f = new XfrZToolFact30();
		}
		return f;
	}
	
	public abstract XfrZTools get(int cz);
}
