package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class SynchronousMachineOPA extends ExportOpenPA<GenList>
{
	public SynchronousMachineOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getGenerators(), SynchronousMachine.values().length);
		int[] bx = bri.get1TBus(_list);
		int[] rx = bri.mapBusFcn(_list, i->_list.getRegBus(i));
		BusList buses = bri.getBuses();
		assign(SynchronousMachine.ID, i -> String.format("\"%s_sm\"", _list.get(i)));
		assign(SynchronousMachine.Name, new StringWrap(i -> _list.getName(i)));
		assign(SynchronousMachine.Node, new StringWrap(i -> buses.get(bx[i]).getID()));
		assign(SynchronousMachine.GeneratingUnit, new StringWrap(i -> _list.getID(i)));
		assign(SynchronousMachine.RegulatedNode,
			new StringWrap(i -> buses.get(rx[i]).getID()));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.SynchronousMachine.toString();
	}
}
