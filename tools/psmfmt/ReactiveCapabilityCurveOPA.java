package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class ReactiveCapabilityCurveOPA extends ExportOpenPA<GenList>
{

	ReactiveCapabilityCurveOPA(PAModel m) throws PAModelException
	{
		super(m.getGenerators(), ReactiveCapabilityCurve.values().length);
		assignCurve1();
		assignCurve2();
	}

	void assignCurve1() throws PAModelException
	{
		assign(ReactiveCapabilityCurve.ID, i -> String.format("\"%s_crv1\"",_list.getID(i)));
		assign(ReactiveCapabilityCurve.SynchronousMachine,
			i -> String.format("\"%s\"", _list.get(i)));
		assign(ReactiveCapabilityCurve.MW, i -> String.valueOf(_list.getOpMinP(i)));
		assign(ReactiveCapabilityCurve.MinMVAr, i -> String.valueOf(_list.getMinQ(i)));
		assign(ReactiveCapabilityCurve.MaxMVAr, i -> String.valueOf(_list.getMaxQ(i)));
	}
	
	void assignCurve2() throws PAModelException
	{
		assign(ReactiveCapabilityCurve.ID, i -> String.format("\"%s_crv2\"",_list.getID(i)));
		assign(ReactiveCapabilityCurve.SynchronousMachine,
			i -> String.format("\"%s\"", _list.get(i)));
		assign(ReactiveCapabilityCurve.MW, i -> String.valueOf(_list.getOpMaxP(i)));
		assign(ReactiveCapabilityCurve.MinMVAr, i -> String.valueOf(_list.getMinQ(i)));
		assign(ReactiveCapabilityCurve.MaxMVAr, i -> String.valueOf(_list.getMaxQ(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.ReactiveCapabilityCurve.toString();
	}
}
