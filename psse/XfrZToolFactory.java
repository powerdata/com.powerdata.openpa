package com.powerdata.openpa.psse;

public abstract class XfrZToolFactory
{
	static XfrZToolFactory Open(int psseversion)
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
