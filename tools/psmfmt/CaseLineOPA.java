package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class CaseLineOPA extends ExportOpenPA<LineList>
{

	public CaseLineOPA(PAModel m) throws PAModelException
	{
		super(m.getLines(), CaseLine.values().length);
		assign(CaseLine.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseLine.FromMW, i -> String.valueOf(_list.getFromP(i)));
		assign(CaseLine.FromMVAr, i -> String.valueOf(_list.getFromQ(i)));
		assign(CaseLine.ToMW, i -> String.valueOf(_list.getToP(i)));
		assign(CaseLine.ToMVAr, i -> String.valueOf(_list.getToQ(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.Line.toString();
	}
}
