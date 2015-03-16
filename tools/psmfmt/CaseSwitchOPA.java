package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SwitchList;

public class CaseSwitchOPA extends ExportOpenPA<SwitchList>
{
	public CaseSwitchOPA(PAModel m) throws PAModelException
	{
		super(m.getSwitches(), CaseSwitch.values().length);
		assign(CaseSwitch.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseSwitch.SwitchPosition,
			new StringWrap(i -> _list.getState(i).toString()));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.Switch.toString();
	}
}
