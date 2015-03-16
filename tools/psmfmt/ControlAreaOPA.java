package com.powerdata.openpa.tools.psmfmt;

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
