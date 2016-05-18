package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
