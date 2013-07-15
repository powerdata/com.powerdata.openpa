package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;

public class XfrWndcw2 extends XfrWndTool
{

	@Override
	public float getRatio1(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return list.getWINDV1(ndx) / list.getBus1(ndx).getBASKV();
	}

	@Override
	public float getRatio2(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return list.getWINDV2(ndx) / list.getBus2(ndx).getBASKV();
	}

	@Override
	public float getRatio3(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return list.getWINDV3(ndx) / list.getBus3(ndx).getBASKV();
	}

}
