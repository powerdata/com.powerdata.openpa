package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifterList;
import com.powerdata.openpa.TransformerList;

public class CaseTransformerWindingOPA extends ExportOpenPA<TransformerList>
{
	protected int _ntx, _nps;
	protected FmtInfo[] _txi;
	
	public CaseTransformerWindingOPA(PAModel m) throws PAModelException
	{
		super(null, TransformerWinding.values().length);
		TransformerList tlist = m.getTransformers();
		PhaseShifterList plist = m.getPhaseShifters();
		_ntx = tlist.size();
		_nps = plist.size();
		assign(tlist);
		_txi = _finfo.clone();
		assign(plist);
	}

	protected void assign(ACBranchListIfc<? extends ACBranch> list) throws PAModelException
	{
		assign(CaseTransformerWinding.ID, new StringWrap(i -> list.getID(i)+":wnd1"));
		assign(CaseTransformerWinding.FromMW, i -> String.valueOf(list.getFromP(i)));
		assign(CaseTransformerWinding.FromMVAr, i -> String.valueOf(list.getFromQ(i)));
		assign(CaseTransformerWinding.ToMW, i -> String.valueOf(list.getToP(i)));
		assign(CaseTransformerWinding.ToMVAr, i -> String.valueOf(list.getToQ(i)));
		assign(CaseTransformerWinding.InService, i -> String.valueOf(list.isInService(i)));
	}

	@Override
	public void export(File outputdir) throws PAModelException, IOException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outputdir, getPsmFmtName()+".csv"))));
		printHeader(pw);
		printData(pw, _txi, _ntx);
		printData(pw, _finfo, _nps);
		pw.close();
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return "PsmCase" + PsmCaseFmtObject.TransformerWinding.toString();
	}
}
