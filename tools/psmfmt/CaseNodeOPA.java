package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class CaseNodeOPA extends ExportOpenPA<BusList>
{

	protected CaseNodeOPA(PAModel m) throws PAModelException
	{
		super(m.getBuses(), CaseNode.values().length);
		assign(CaseNode.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseNode.Ang, i -> String.valueOf(_list.getVA(i)));
		assign(CaseNode.Mag, i -> String.valueOf(_list.getVM(i)));
	}

	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase"+PsmCaseFmtObject.Node.toString();
	}
}
