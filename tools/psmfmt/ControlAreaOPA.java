package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.AreaList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class ControlAreaOPA extends ExportOpenPA<AreaList>
{
	public ControlAreaOPA(PAModel m) throws PAModelException
	{
		super(m.getAreas(), ControlArea.values().length);
		assign(ControlArea.ID, new StringWrap(i -> _list.getID(i)));
		assign(ControlArea.Name, new StringWrap(i -> _list.getName(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.ControlArea.toString();
	}

}
