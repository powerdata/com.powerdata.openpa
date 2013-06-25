package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public abstract class XfrMagYTool
{
	protected static final XfrMagYTool[] _ToolSet;
	
	static
	{
		XfrMagYTool cm1 = new XfrMagYcm1();
		_ToolSet = new XfrMagYTool[]
		{
			cm1,
			cm1,
			new XfrMagYcm2()
		};
		
	}
	
	public static XfrMagYTool get(int cm) {return _ToolSet[cm];}

	public abstract Complex getMagY(TransformerInList list, int ndx) throws PsseModelException;
}
