package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.tools.Complex;

public class XfrMagYcm2 extends XfrMagYTool
{

	@Override
	protected Complex _getYMag(Transformer xf) throws PsseModelException
	{
		return cvt(xf.getFromBus().getBASKV(), xf.getNOMV1(), xf.getMAG1(), xf.getMAG2(), xf.getSBASE1_2());
	}
	@Override
	protected Complex _getYMag(PhaseShifter xf) throws PsseModelException
	{
		return cvt(xf.getFromBus().getBASKV(), xf.getNOMV1(), xf.getMAG1(), xf.getMAG2(), xf.getSBASE1_2());
	}
	
	protected Complex cvt(float baskv, float nomv1, float mag1, float mag2, float sbase)
	{
		float vratio = baskv / nomv1; 
		float vrsq = vratio * vratio;
		float ghe = mag1 / 1e+08F * vrsq;
		float ymabs = mag2 * (sbase / 100F) * vrsq;
		return new Complex(ghe, (float) Math.sqrt(ymabs * ymabs - ghe * ghe));
	}
}
