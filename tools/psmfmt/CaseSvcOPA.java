package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVCList;

public class CaseSvcOPA extends ExportOpenPA<SVCList>
{
	public CaseSvcOPA(PAModel m) throws PAModelException
	{
		super(m.getSVCs(), CaseSVC.values().length);
		assign(CaseSVC.ID, new StringWrap(i -> _list.getID(i)));
		assign(CaseSVC.Mode,
			new StringWrap(i -> _list.isRegKV(i)?"Volt":"MVAr"));
		assign(CaseSVC.MVArSetPoint, i -> String.valueOf(_list.getQS(i)));
		assign(CaseSVC.VoltageSetpoint, i -> String.valueOf(_list.getVS(i)));
		assign(CaseSVC.InService, i -> String.valueOf(_list.isInService(i)));
	}


	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.SVC.toString();
	}
}
