package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class XfrMagYcm2 extends XfrMagYTool
{

	@Override
	protected Complex _getYMag(TransformerRawList list, int ndx) throws PsseModelException
	{
		float vratio = list.getBusI(ndx).getBASKV() / list.getNOMV1(ndx); 
		float vrsq = vratio * vratio;
		float ghe = list.getMAG1(ndx) / 1e+08F * vrsq;
		float ymabs = list.getMAG2(ndx) * (list.getSBASE1_2(ndx) / 100F) * vrsq;
		return new Complex(ghe, (float) Math.sqrt(ymabs * ymabs - ghe * ghe));
	}
}
