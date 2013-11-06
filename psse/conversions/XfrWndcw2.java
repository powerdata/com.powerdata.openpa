package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.util.TransformerRaw;

public class XfrWndcw2 extends XfrWndTool
{

	@Override
	public float getRatio1(TransformerRaw xf) throws PsseModelException
	{
		return xf.getWINDV1() / xf.getBusI().getBASKV();
	}

	@Override
	public float getRatio2(TransformerRaw xf) throws PsseModelException
	{
		return xf.getWINDV2() / xf.getBusJ().getBASKV();
	}

	@Override
	public float getRatio3(TransformerRaw xf) throws PsseModelException
	{
		return xf.getWINDV3() / xf.getBusK().getBASKV();
	}
	
	@Override
	public float getRatio1(Transformer xf) throws PsseModelException
	{
		return xf.getWINDV1() / xf.getFromBus().getBASKV();
	}

	@Override
	public float getRatio2(Transformer xf) throws PsseModelException
	{
		return xf.getWINDV2() / xf.getToBus().getBASKV();
	}

	@Override
	public float getRatio1(PhaseShifter xf) throws PsseModelException
	{
		return xf.getWINDV1() / xf.getFromBus().getBASKV();
	}

}
