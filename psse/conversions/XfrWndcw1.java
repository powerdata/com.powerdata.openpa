package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.util.TransformerRaw;

public class XfrWndcw1 extends XfrWndTool
{

	@Override
	public float getRatio1(TransformerRaw xf) throws PsseModelException {return xf.getWINDV1();}
	@Override
	public float getRatio2(TransformerRaw xf) throws PsseModelException {return xf.getWINDV2();}
	@Override
	public float getRatio3(TransformerRaw xf) throws PsseModelException {return xf.getWINDV3();}
	@Override
	public float getRatio1(Transformer xf) throws PsseModelException {return xf.getWINDV1();}
	@Override
	public float getRatio2(Transformer xf) throws PsseModelException {return xf.getWINDV2();}
	@Override
	public float getRatio1(PhaseShifter ps) throws PsseModelException {return ps.getWINDV1();}
}
