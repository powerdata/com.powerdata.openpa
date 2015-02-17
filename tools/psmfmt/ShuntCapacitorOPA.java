package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.ShuntCapList;

public class ShuntCapacitorOPA extends ExportOpenPA<ShuntCapList>
{
	public ShuntCapacitorOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getShuntCapacitors(), ShuntCapacitor.values().length);
		int[] bx = bri.get1TBus(_list);
		assign(ShuntCapacitor.ID, new StringWrap(i -> _list.getID(i)));
		assign(ShuntCapacitor.Name, new StringWrap(i -> _list.getName(i)));
		assign(ShuntCapacitor.Node, new StringWrap(i -> bri.getBuses().get(bx[i]).getID()));
		assign(ShuntCapacitor.MVAr, i -> String.valueOf(_list.getB(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.ShuntCapacitor.toString();
	}
}
