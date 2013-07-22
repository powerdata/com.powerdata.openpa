package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;

public class XfrZTools30cz3 implements XfrZTools
{
	@Override
	public Complex convert2W(Transformer xf) throws PsseModelException
	{
		return cvt(xf.getR1_2(), xf.getX1_2(), xf.getSBASE1_2());
	}

	@Override
	public Complex convert2W(TransformerRaw xf) throws PsseModelException
	{
		return cvt(xf.getR1_2(), xf.getX1_2(), xf.getSBASE1_2());
	}

	@Override
	public DeltaNetwork convert3W(TransformerRaw xf) throws PsseModelException
	{
		return new DeltaNetwork(
			cvt(xf.getR1_2(), xf.getX1_2(), xf.getSBASE1_2()),
			cvt(xf.getR2_3(), xf.getX2_3(), xf.getSBASE2_3()),
			cvt(xf.getR3_1(), xf.getX3_1(), xf.getSBASE3_1()));
	}

	protected Complex cvt(float r, float x, float sbase)
	{
		r /= (1e+6F * sbase);
		return new Complex(r, (float) Math.sqrt(x * x - r * r));
	}
}
