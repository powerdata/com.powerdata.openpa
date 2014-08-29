package com.powerdata.openpa.tools.psmfmt;

import java.util.EnumMap;
import java.util.Map;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

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
		assign(CaseSynchronousMachine.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseSynchronousMachine.AVRMode, i -> _list.isRegKV(i)?"ON":"OFF");
		assign(CaseSynchronousMachine.SynchronousMachineOperatingMode,
			i -> _GenMode.getOrDefault(_list.getMode(i), "GEN"));
		assign(CaseSynchronousMachine.KVSetPoint, i -> String.valueOf(_list.getVS(i)));
		assign(CaseSynchronousMachine.MVArSetpoint, i -> String.valueOf(_list.getQS(i)));
		assign(CaseSynchronousMachine.MVAr, i -> String.valueOf(_list.getQ(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.SynchronousMachine.toString();
	}
}
