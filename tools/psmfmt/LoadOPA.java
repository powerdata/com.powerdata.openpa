package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class LoadOPA extends ExportOpenPA<LoadList>
{
	public LoadOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getLoads(), Load.values().length);
		int[] bx = bri.get1TBus(_list);
		assign(Load.ID, new StringWrap(i -> _list.getID(i)));
		assign(Load.Name, new StringWrap(i -> _list.getName(i)));
		assign(Load.Node, new StringWrap(i -> bri.getBuses().get(bx[i]).getID()));
	}
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Load.toString();
	}
}
