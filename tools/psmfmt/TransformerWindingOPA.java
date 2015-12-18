package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifterList;
import com.powerdata.openpa.TransformerList;

public class TransformerWindingOPA extends ExportOpenPA<ACBranchListIfc<? extends ACBranch>>
{
	protected int _ntx, _nps;
	protected FmtInfo[] _txi;
	
	public TransformerWindingOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(null, TransformerWinding.values().length);
		TransformerList tlist = m.getTransformers();
		PhaseShifterList plist = m.getPhaseShifters();
		_ntx = tlist.size();
		_nps = plist.size();
		assign(tlist, bri);
		_txi = _finfo.clone();
		assign(plist, bri);
	}

	void assign(ACBranchListIfc<? extends ACBranch> list, BusRefIndex bri) throws PAModelException
	{
		BusRefIndex.TwoTerm bx = bri.get2TBus(list);
		BusList buses = bri.getBuses();
		assign(TransformerWinding.ID, new StringWrap(i -> list.getID(i)+":wnd1"));
		assign(TransformerWinding.Name, new StringWrap(i -> list.getName(i)));
		assign(TransformerWinding.Transformer, new StringWrap(i -> list.getID(i)));
		assign(TransformerWinding.Node1, new StringWrap(i -> buses.get(bx.getFromBus()[i]).getID()));
		assign(TransformerWinding.Node2, new StringWrap(i -> buses.get(bx.getToBus()[i]).getID()));
		assign(TransformerWinding.R, i -> String.valueOf(list.getR(i)));
		assign(TransformerWinding.X, i -> String.valueOf(list.getX(i)));
		assign(TransformerWinding.Bmag, i -> String.valueOf(list.getBmag(i)));
		assign(TransformerWinding.NormalOperatingLimit, 
			i -> String.valueOf(list.getLTRating(i)));
		
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
		return PsmMdlFmtObject.TransformerWinding.toString();
	}
	
}
