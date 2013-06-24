package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;

public class XfrZTools30LossWndBase implements XfrZTools
{

	@Override
	public Complex convert2W(TransformerInList list, int ndx)
	{
		return cvt(list.getR1_2(ndx), list.getX1_2(ndx), list.getSBASE1_2(ndx));
	}

	@Override
	public DeltaNetwork convert3W(TransformerInList list, int ndx)
	{
		return new DeltaNetwork(
			cvt(list.getR1_2(ndx), list.getX1_2(ndx), list.getSBASE1_2(ndx)),
			cvt(list.getR2_3(ndx), list.getX2_3(ndx), list.getSBASE2_3(ndx)),
			cvt(list.getR3_1(ndx), list.getX3_1(ndx), list.getSBASE3_1(ndx)));
	}

	protected Complex cvt(float r, float x, float sbase)
	{
		r /= (1e+6F * sbase);
		return new Complex(r, (float) Math.sqrt(x * x - r * r));
	}
}
