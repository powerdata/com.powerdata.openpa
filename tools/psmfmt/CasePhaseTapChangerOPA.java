package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.PhaseShifterList;

public class CasePhaseTapChangerOPA extends ExportOpenPA<PhaseShifterList>
{
	public CasePhaseTapChangerOPA(PAModel m) throws PAModelException
	{
		super(m.getPhaseShifters(), CasePhaseTapChanger.values().length);
		assign(CasePhaseTapChanger.ID, new StringWrap(i -> _list.getID(i)+":tap"));
		assign(CasePhaseTapChanger.ControlStatus,
			i -> String.valueOf(_list.getControlMode(i) == ControlMode.FixedMW));
		assign(CasePhaseTapChanger.PhaseShift,
			i -> String.valueOf(_list.getShift(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.PhaseTapChanger.toString();
	}
}
