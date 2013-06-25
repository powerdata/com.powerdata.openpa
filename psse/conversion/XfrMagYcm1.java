package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.TransformerInList;
import com.powerdata.openpa.tools.Complex;

public class XfrMagYcm1 extends XfrMagYTool
{

	@Override
	public Complex getMagY(TransformerInList list, int ndx)
	{
		return new Complex(list.getMAG1(ndx), list.getMAG2(ndx));
	}

}
