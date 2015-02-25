package com.powerdata.openpa.tools.psmfmt;

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
