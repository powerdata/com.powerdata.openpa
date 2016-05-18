package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;


public class GeneratingUnitOPA extends ExportOpenPA<GenList>
{
	public GeneratingUnitOPA(PAModel m) throws PAModelException
	{
		super(m.getGenerators(), GeneratingUnit.values().length);
		
		assign(GeneratingUnit.ID, new StringWrap(i -> _list.getID(i)));
		assign(GeneratingUnit.Name, new StringWrap(i -> _list.getName(i)));
		assign(GeneratingUnit.MinOperatingMW, i -> String.valueOf(_list.getOpMinP(i)));
		assign(GeneratingUnit.MaxOperatingMW, i -> String.valueOf(_list.getOpMaxP(i)));
		assign(GeneratingUnit.GeneratingUnitType, new StringWrap(i -> _list.getType(i).toString()));
//		assign(GeneratingUnit.GenControlMode, new StringWrap(i -> _list.getMode(i).toString())); //Wrong mode, the correct one is not currently handled by OpenPA
		assign(GeneratingUnit.GenControlMode, (i -> "Setpoint")); //Defaulting to setpoint for now
	}

	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.GeneratingUnit.toString();
	}
}
