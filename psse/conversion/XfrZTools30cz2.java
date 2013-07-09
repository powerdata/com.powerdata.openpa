package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;
import com.powerdata.openpa.tools.PAMath;

public class XfrZTools30cz2 implements XfrZTools
{

	@Override
	public Complex convert2W(TransformerRawList list, int ndx) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(list.getR1_2(ndx), list.getX1_2(ndx)),
				list.getSBASE1_2(ndx));
	}

	@Override
	public DeltaNetwork convert3W(TransformerRawList list, int ndx) throws PsseModelException
	{
		return new DeltaNetwork(
			PAMath.rebaseZ100(new Complex(list.getR1_2(ndx), list.getX1_2(ndx)), list.getSBASE1_2(ndx)),
			PAMath.rebaseZ100(new Complex(list.getR2_3(ndx), list.getX2_3(ndx)), list.getSBASE2_3(ndx)),
			PAMath.rebaseZ100(new Complex(list.getR3_1(ndx), list.getX3_1(ndx)), list.getSBASE3_1(ndx)));
	}

}
