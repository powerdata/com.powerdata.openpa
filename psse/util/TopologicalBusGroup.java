package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Switch;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.tools.LinkNet;

public class TopologicalBusGroup extends BusGroup
{

	public TopologicalBusGroup(PsseModel model, GroupBuilder gbld)
			throws PsseModelException
	{
		super(model, gbld);
	}

	public TopologicalBusGroup(PsseModel model) throws PsseModelException
	{
		super(model, new GroupBuilder()
		{
			@Override
			public LinkNet build(PsseModel model) throws PsseModelException
			{
				LinkNet rv = new LinkNet();
				BusList buses = model.getBuses();
				SwitchList switches = model.getSwitches();
				rv.ensureCapacity(buses.size()-1, switches.size());
				rv.addBuses(0, buses.size());
				
				for(Switch s : switches)
				{
					rv.addBranch(s.getFromBus().getIndex(),
						s.getToBus().getIndex());
				}
				
				return rv;
			}
		});
	}
	
}
