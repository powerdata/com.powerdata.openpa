package com.powerdata.openpa.psse.conversions;

public abstract class XfrZToolFactory
{
	static XfrZToolFactory _v30 = new XfrZToolFact30();
	
	public static XfrZToolFactory Open(int psseversion)
	{
		if (psseversion <= 30)
		{
			return _v30;
		}
		return null;
	}
	
	public abstract XfrZTools get(int cz);
}
