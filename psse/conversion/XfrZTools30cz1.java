package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerInList;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;

public class XfrZTools30cz1 implements XfrZTools
{

	@Override
	public Complex convert2W(TransformerInList list, int ndx) throws PsseModelException
	{
		return new Complex(list.getR1_2(ndx), list.getX1_2(ndx));
	}

	@Override
	public DeltaNetwork convert3W(TransformerInList list, int ndx)
	{
		return new DeltaNetwork(
			new Complex(list.getR1_2(ndx), list.getX1_2(ndx)),
			new Complex(list.getR2_3(ndx), list.getX2_3(ndx)),
			new Complex(list.getR3_1(ndx), list.getX3_1(ndx)));
	}

}
