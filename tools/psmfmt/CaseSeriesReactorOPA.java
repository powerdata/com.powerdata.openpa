package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesReacList;

public class CaseSeriesReactorOPA extends ExportOpenPA<SeriesReacList>
{

	public CaseSeriesReactorOPA(PAModel m) throws PAModelException
	{
		super(m.getSeriesReactors(), CaseSeriesReactor.values().length);
		assign(CaseSeriesReactor.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseSeriesReactor.FromMW, i -> String.valueOf(_list.getFromP(i)));
		assign(CaseSeriesReactor.FromMVAr, i -> String.valueOf(_list.getFromQ(i)));
		assign(CaseSeriesReactor.ToMW, i -> String.valueOf(_list.getToP(i)));
		assign(CaseSeriesReactor.ToMVAr, i -> String.valueOf(_list.getToQ(i)));
		assign(CaseSeriesReactor.InService, i -> String.valueOf(_list.isInService(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.SeriesReactor.toString();
	}
}
