package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
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
	
	public static Complex getYMag(Transformer xf) throws PsseModelException
	{
		return _ToolSet[xf.getCM()]._getYMag(xf);
	}

	protected abstract Complex _getYMag(Transformer xf) throws PsseModelException;

	public static Complex getYMag(PhaseShifter xf) throws PsseModelException
	{
		return _ToolSet[xf.getCM()]._getYMag(xf);
	}

	protected abstract Complex _getYMag(PhaseShifter xf) throws PsseModelException;
}
