package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class XfrMagYcm1 extends XfrMagYTool
{

	@Override
	protected Complex _getYMag(TransformerRawList list, int ndx) throws PsseModelException
	{
		return new Complex(list.getMAG1(ndx), list.getMAG2(ndx));
	}

}
