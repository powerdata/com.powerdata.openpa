package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.tools.psmfmt.ExportOpenPA.StringWrap;

public class CaseTransformerWindingOPA extends ExportOpenPA<TransformerList>
{

	CaseTransformerWindingOPA(PAModel m) throws PAModelException
	{
		super(m.getTransformers(), CaseTransformerWinding.values().length);
		assign(TransformerWinding.ID, new StringWrap(i -> _list.getID(i)+"_wnd"));
		assign(CaseTransformerWinding.FromMW, i -> String.valueOf(_list.getFromP(i)));
		assign(CaseTransformerWinding.FromMVAr, i -> String.valueOf(_list.getFromQ(i)));
		assign(CaseTransformerWinding.ToMW, i -> String.valueOf(_list.getToP(i)));
		assign(CaseTransformerWinding.ToMVAr, i -> String.valueOf(_list.getToQ(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.TransformerWinding.toString();
	}
}
