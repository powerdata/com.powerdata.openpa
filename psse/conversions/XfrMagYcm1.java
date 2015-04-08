package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.tools.Complex;

public class XfrMagYcm1 extends XfrMagYTool
{
	@Override
	protected Complex _getYMag(Transformer xf) throws PsseModelException
	{
		return new Complex(xf.getMAG1(), xf.getMAG2());
	}
	@Override
	protected Complex _getYMag(PhaseShifter xf) throws PsseModelException
	{
		return new Complex(xf.getMAG1(), xf.getMAG2());
	}
}
