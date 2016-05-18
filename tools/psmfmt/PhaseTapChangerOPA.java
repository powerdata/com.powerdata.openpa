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
import com.powerdata.openpa.PhaseShifterList;

public class PhaseTapChangerOPA extends ExportOpenPA<PhaseShifterList>
{
	public PhaseTapChangerOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getPhaseShifters(), PhaseTapChanger.values().length);
		int[] bx = bri.get2TBus(_list).getToBus();
		assign(PhaseTapChanger.ID, i -> String.format("\"%s:tap\"", _list.getID(i)));
		assign(PhaseTapChanger.TapNode, new StringWrap(i -> bri.getBuses().get(bx[i]).getID()));
		assign(PhaseTapChanger.TransformerWinding,
			i -> String.format("\"%s:wnd1\"", _list.getID(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.PhaseTapChanger.toString();
	}
}
