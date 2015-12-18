package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.Gen;
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
		assign(SynchronousMachine.ID, new StringWrap(i -> createID(_list.get(i))));
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
	
	public static String createID(Gen g) throws PAModelException
	{
		//IDs as of 2/19/2015
		//Generator: "Company:Station:Class:Name"
		//SynchMach: "Company:Station:Voltage:Class:Name"
		String[] idBase = g.getID().split(":");
		if(idBase.length == 4)
		{
			//ID has expected number of ':'
			return idBase[0]+":"
					+idBase[1]+":"
					+g.getBus().getVoltageLevel().getName()
					+":SynchronousMachine:"
					+g.getName();
		}
		else
		{
//			System.out.println("\n[SynchronousMachineOPA.java] ID \""+g.getID()+"\" split into "+idBase.length+" parts");
//			System.out.println("[SynchronousMachineOPA.java] Generator ID likely contains at least one \":\". For now the ID is being set as the generator's ID appened with \":SM\"");
			return g.toString()+":SM";
		}
	}
}
