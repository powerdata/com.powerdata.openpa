package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class ReactiveCapabilityCurveOPA extends ExportOpenPA<GenList>
{
	protected FmtInfo[] _fi;
	
	public ReactiveCapabilityCurveOPA(PAModel m) throws PAModelException
	{
		super(m.getGenerators(), ReactiveCapabilityCurve.values().length);
		_fi = new FmtInfo[ReactiveCapabilityCurve.values().length];
		assignCurve1();
		assignCurve2();
	}

	void assignCurve1() throws PAModelException
	{
		assign(ReactiveCapabilityCurve.ID,
				i -> String.format("\"%s_crv1\"",_list.getID(i)));
		assign(ReactiveCapabilityCurve.SynchronousMachine,
				i -> SynchronousMachineOPA.createID(_list.get(i)));
		assign(ReactiveCapabilityCurve.MW, 
				i -> String.valueOf(_list.getOpMinP(i)));
		assign(ReactiveCapabilityCurve.MinMVAr, 
				i -> String.valueOf(_list.getMinQ(i)));
		assign(ReactiveCapabilityCurve.MaxMVAr,
				i -> String.valueOf(_list.getMaxQ(i)));
	}
	
	void assignCurve2() throws PAModelException
	{
		assign(ReactiveCapabilityCurve.ID,
				i -> String.format("\"%s_crv2\"",_list.getID(i)),
				_fi);
		assign(ReactiveCapabilityCurve.SynchronousMachine,
				i -> SynchronousMachineOPA.createID(_list.get(i)),
				_fi);
		assign(ReactiveCapabilityCurve.MW, 
				i -> String.valueOf(_list.getOpMaxP(i)),
				_fi);
		assign(ReactiveCapabilityCurve.MinMVAr,
				i -> String.valueOf(_list.getMinQ(i)),
				_fi);
		assign(ReactiveCapabilityCurve.MaxMVAr,
				i -> String.valueOf(_list.getMaxQ(i)),
				_fi);
	}
	
	@Override
	public void export(File outputdir) throws IOException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(outputdir, getPsmFmtName()+".csv"))));
		printHeader(pw);
		printData(pw, _finfo, getCount());
		printData(pw, _fi, getCount());
		pw.close();
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.ReactiveCapabilityCurve.toString();
	}
}
