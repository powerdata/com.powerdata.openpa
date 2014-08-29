package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.pwrflow.BusRefIndex;

public class RatioTapChangerOPA extends ExportOpenPA<TransformerList>
{
	FmtInfo[] _lfi;
	
	public RatioTapChangerOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getTransformers(), RatioTapChanger.values().length);
		int[][] bx = bri.get2TBus(_list);
		assignTap(bx[0], bri, 'f');
		_lfi = _finfo.clone();
		assignTap(bx[1], bri, 't');
		
	}
	
	void assignTap(int[] tnode, BusRefIndex bri, char side)
	{
		assign(RatioTapChanger.ID, i -> String.format("\"%s_%ctap\"", _list.getID(i), side));
		assign(RatioTapChanger.TapNode, new StringWrap(i -> bri.getBuses().get(tnode[i]).getID()));
		assign(RatioTapChanger.TransformerWinding,
			i -> String.format("\"%s_%cwnd\"", _list.getID(i), side));
	}


	@Override
	void export(File outputdir) throws PAModelException, IOException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outputdir, getPsmFmtName()+".csv"))));
		printHeader(pw);
		printData(pw, _lfi);
		printData(pw);
		pw.close();

	}

	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.RatioTapChanger.toString();
	}
}
