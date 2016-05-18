package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class CaseNodeOPA extends ExportOpenPA<BusList>
{

	protected CaseNodeOPA(PAModel m) throws PAModelException
	{
		super(m.getBuses(), CaseNode.values().length);
		assign(CaseNode.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseNode.Ang, i -> String.valueOf(_list.getVA(i)));
		assign(CaseNode.Mag, i -> String.valueOf(_list.getVM(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase"+PsmCaseFmtObject.Node.toString();
	}
}
