package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LinkNet;

public class IslandBusGroup extends BusGroup
{
	protected boolean		_energized;
	protected int[]			_genbus;
	
	public IslandBusGroup(PsseModel model, GroupBuilder gbld)
			throws PsseModelException
	{
		super(model, gbld);
		initEnerStatus(model);
	}

	public IslandBusGroup(PsseModel model) throws PsseModelException
	{
		super(model, new GroupBuilder()
		{
			@Override
			public LinkNet build(PsseModel model) throws PsseModelException
			{
				LinkNet rv = new LinkNet();
				ACBranchList acb = model.getBranches();
				BusList buses = model.getBuses();
				rv.ensureCapacity(buses.size() - 1, acb.size());
				rv.addBuses(0, buses.size());

				for (ACBranch d : model.getBranches())
				{
					rv.addBranch(d.getFromBus().getIndex(),
						d.getToBus().getIndex());
				}

				return rv;
			}
		});
		initEnerStatus(model);
	}

	protected void initEnerStatus(PsseModel model) throws PsseModelException
	{
		for (Gen g : model.getGenerators())
		{
			switch(g.getMode())
			{
				case OFF:
				case PMP:
				case CON:
					break;
				default:
					_energized = true;
					return;
			}
		}
	}
}
