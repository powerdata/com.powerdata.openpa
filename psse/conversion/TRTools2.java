package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TapRatioLimit;
import com.powerdata.openpa.psse.TransformerRawList;

public abstract class TRTools2
{
	public abstract TapRatioLimit getRatioLim1(TransformerRawList list, int ndx) throws PsseModelException;
	public abstract TapRatioLimit getRatioLim2(TransformerRawList list, int ndx);
	public abstract TapRatioLimit getRatioLim3(TransformerRawList list, int ndx);
}
