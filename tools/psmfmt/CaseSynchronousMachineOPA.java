package com.powerdata.openpa.tools.psmfmt;

import java.util.EnumMap;
import java.util.Map;

import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.psmfmt.ExportOpenPA.StringWrap;

public class CaseSynchronousMachineOPA extends ExportOpenPA<GenList>
{
	static Map<Gen.Mode, String> _GenMode = new EnumMap<>(Gen.Mode.class);
	static
	{
		_GenMode.put(Gen.Mode.CON, "CON");
		_GenMode.put(Gen.Mode.PMP, "PMP");
	}
	
	public CaseSynchronousMachineOPA(PAModel m) throws PAModelException
	{
		super(m.getGenerators(), CaseSynchronousMachine.values().length);
		//assign(CaseSynchronousMachine.ID, i -> String.format("\"%s\"", _list.get(i)));
		assign(CaseSynchronousMachine.ID, new StringWrap(i -> createID(_list.get(i))));
//		assign(CaseSynchronousMachine.AVRMode, i -> _list.isRegKV(i)?"ON":"OFF");
		assign(CaseSynchronousMachine.AVRMode, i -> !_list.isRegKV(i)?"ON":"OFF"); //RegKV comes from pd3openpa.pddef which has reversed AVRMode to get RegMode, just reversing it back here
		assign(CaseSynchronousMachine.SynchronousMachineOperatingMode,
			i -> _GenMode.getOrDefault(_list.getMode(i), "GEN"));
		assign(CaseSynchronousMachine.KVSetPoint, i -> String.valueOf(_list.getVS(i)));
		assign(CaseSynchronousMachine.MVArSetpoint, i -> String.valueOf(_list.getQS(i)));
		assign(CaseSynchronousMachine.MVAr, i -> String.valueOf(_list.getQ(i)));
		assign(CaseSynchronousMachine.InService, i -> String.valueOf(_list.isInService(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.SynchronousMachine.toString();
	}
	
	private String createID(Gen g) throws PAModelException
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
			System.out.println("\n[SynchronousMachineOPA.java] ID \""+g.getID()+"\" split into "+idBase.length+" parts");
			System.out.println("[SynchronousMachineOPA.java] Generator ID likely contains at least one \":\". For now the ID is being set as the generator's ID appened with \":SM\"");
			return g.toString()+":SM";
		}
		
	}
}
