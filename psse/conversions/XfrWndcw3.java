package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerRawList;

public class XfrWndcw3 extends XfrWndTool
{

	@Override
	public float getRatio1(TransformerRawList list, int ndx) throws PsseModelException
	{
		return list.getWINDV1(ndx) * (list.getNOMV1(ndx) / list.getBusI(ndx).getBASKV());
	}

	@Override
	public float getRatio2(TransformerRawList list, int ndx) throws PsseModelException
	{
		return list.getWINDV2(ndx) * (list.getNOMV2(ndx) / list.getBusJ(ndx).getBASKV());
	}

	@Override
	public float getRatio3(TransformerRawList list, int ndx) throws PsseModelException
	{
		return list.getWINDV3(ndx) * (list.getNOMV3(ndx) / list.getBusK(ndx).getBASKV());
	}

}
