package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TransformerRaw;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;
import com.powerdata.openpa.tools.PAMath;

public class XfrZTools30cz2 implements XfrZTools
{
	@Override
	public Complex convert2W(Transformer xf) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(xf.getR1_2(), xf.getX1_2()),
				xf.getSBASE1_2());
	}

	@Override
	public Complex convert2W(PhaseShifter xf) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(xf.getR1_2(), xf.getX1_2()),
				xf.getSBASE1_2());
	}

	@Override
	public Complex convert2W(TransformerRaw xf) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(xf.getR1_2(), xf.getX1_2()),
				xf.getSBASE1_2());
	}

	@Override
	public DeltaNetwork convert3W(TransformerRaw xf) throws PsseModelException
	{
		return new DeltaNetwork(
			PAMath.rebaseZ100(new Complex(xf.getR1_2(), xf.getX1_2()), xf.getSBASE1_2()),
			PAMath.rebaseZ100(new Complex(xf.getR2_3(), xf.getX2_3()), xf.getSBASE2_3()),
			PAMath.rebaseZ100(new Complex(xf.getR3_1(), xf.getX3_1()), xf.getSBASE3_1()));
	}

}
