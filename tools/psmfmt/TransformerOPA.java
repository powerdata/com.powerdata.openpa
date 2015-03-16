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

public class TransformerOPA extends ExportOpenPA<ACBranchListIfc<? extends ACBranch>>
{
	FmtInfo[] _txi;
	int _ntx, _nps;
	public TransformerOPA(PAModel m) throws PAModelException
	{
		super(null, Transformer.values().length);
		TransformerList tlist = m.getTransformers();
		PhaseShifterList plist = m.getPhaseShifters();
		_ntx = tlist.size();
		_nps = plist.size();
		assign(tlist);
		_txi = _finfo.clone();
		assign(plist);
	}

	void assign(ACBranchListIfc<? extends ACBranch> list)
	{
		assign(Transformer.ID, new StringWrap(i -> list.getID(i)));
		assign(Transformer.Name, new StringWrap(i -> list.getName(i)));
		assign(Transformer.WindingCount, i -> "2");
	}
	
	@Override
	protected String getPsmFmtName() {return PsmMdlFmtObject.Transformer.toString();}

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


}
