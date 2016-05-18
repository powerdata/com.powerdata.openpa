package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVCList;

public class SvcOPA extends ExportOpenPA<SVCList>
{
	public SvcOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getSVCs(), SVC.values().length);
		int[] bx = bri.get1TBus(_list);
		assign(SVC.ID, new StringWrap(i -> _list.getID(i)));
		assign(SVC.Name, new StringWrap(i -> _list.getName(i)));
		assign(SVC.Node, new StringWrap(i -> bri.getBuses().get(bx[i]).getID()));
		assign(SVC.MinMVAr, i -> String.valueOf(_list.getMinQ(i)));
		assign(SVC.MaxMVAr, i -> String.valueOf(_list.getMaxQ(i)));
		assign(SVC.Slope, i -> String.valueOf(_list.getSlope(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.SVC.toString();
	}
}
