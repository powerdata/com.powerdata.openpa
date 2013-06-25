package com.powerdata.openpa.psse;

public class XfrZToolFact30 extends XfrZToolFactory
{
	private static final XfrZTools[]	_ToolSet;
	
	static
	{
		XfrZTools cz1 = new XfrZTools30cz1();
		_ToolSet = new XfrZTools[] 
		{ 	
			cz1,
			cz1,
			new XfrZTools30cz2(),
			new XfrZTools30cz3()
		};
	}

	@Override
	public XfrZTools get(int cz)
	{
		return _ToolSet[cz];
	}
}
