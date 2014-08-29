package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.StationList;

public class SubstationOPA extends ExportOpenPA<StationList>
{
	public SubstationOPA(PAModel m) throws PAModelException
	{
		super(m.getStations(), Substation.values().length);
		assign(Substation.ID, new StringWrap(i -> _list.getID(i)));
		assign(Substation.Name, new StringWrap(i -> _list.getName(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Substation.toString();
	}
}
