package com.powerdata.openpa.tools.psmfmt;

import java.util.EnumMap;
import java.util.Map;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class CaseGeneratingUnitOPA extends ExportOpenPA<GenList>
{
	static Map<Gen.Mode, String> _ModeMap = new EnumMap<>(Gen.Mode.class);
	static
	{
		_ModeMap.put(Gen.Mode.OFF, "OFF");
		_ModeMap.put(Gen.Mode.MAN, "MAN");
		_ModeMap.put(Gen.Mode.AGC, "AGC");
		_ModeMap.put(Gen.Mode.EDC, "EDC");
		_ModeMap.put(Gen.Mode.LFC, "LFC");
	}
	
	
	public CaseGeneratingUnitOPA(PAModel m) throws PAModelException
	{
		super(m.getGenerators(), CaseGeneratingUnit.values().length);
		assign(CaseGeneratingUnit.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseGeneratingUnit.MW, i -> String.valueOf(_list.getP(i)));
		assign(CaseGeneratingUnit.MWSetPoint, i -> String.valueOf(_list.getPS(i)));
		assign(CaseGeneratingUnit.GeneratorOperatingMode,
			i -> _ModeMap.getOrDefault(_list.getMode(i), ""));
		assign(CaseGeneratingUnit.InService, i -> String.valueOf(_list.isInService(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.GeneratingUnit.toString();
	}
}
