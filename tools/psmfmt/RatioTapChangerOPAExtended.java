package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.tools.psmfmt.Export.FmtInfo;
import com.powerdata.openpa.tools.psmfmt.ExportOpenPA.StringWrap;
import com.powerdata.pd3.Column;
import com.powerdata.pd3.PDDB;
import com.powerdata.pd3.PDDBException;
import com.powerdata.pd3.View;

public class RatioTapChangerOPAExtended extends ExportOpenPA<TransformerList>
{
	FmtInfo[] _lfi;
	Column _minKV;
	Column _maxKV;
	Column _hasLTC;
	Column _hSide;
	
	public RatioTapChangerOPAExtended(PAModel m, BusRefIndex bri, PDDB db) throws PAModelException, PDDBException 
	{
		//BusRefIndex.TwoTerm bx = bri.get2TBus(_list);
		super(m.getTransformers(), RatioTapChanger.values().length);
		View xfrView = db.openView("view:group=pd3openpa&name=Xfr&ctx=Ots&inputctx=OtsControl", null);
		_minKV 	= xfrView.col("minkv");
		_maxKV 	= xfrView.col("maxkv");
		_hasLTC = xfrView.col("hasLTC");
		_hSide	= xfrView.col("hside");
		
//		System.out.println("[RatioTapChangerOPAExtended] Number of keys: "+_list.getKeys().length);
		BusRefIndex.TwoTerm bx = bri.get2TBus(_list);
		assignTap(bx.getFromBus(), bri, 'f');
		_lfi = _finfo.clone();
		assignTap(bx.getToBus(), bri, 't');
	}
	
	void assignTap(int[] tnode, BusRefIndex bri, char side)
	{
		assign(RatioTapChanger.ID,
				i -> String.format("\"%s:%ctap\"", _list.getID(i), side));
		assign(RatioTapChanger.TapNode,
				new StringWrap(i -> bri.getBuses().get(tnode[i]).getID()));
		assign(RatioTapChanger.TransformerWinding,
				new StringWrap(i -> _list.getID(i)+":wnd1"));
		assign(RatioTapChanger.MinKV,
				i-> getKV(false, side, _list.getKey(i)));
		assign(RatioTapChanger.MaxKV,
				i -> getKV(true, side, _list.getKey(i)));
	}
	
	String getKV(boolean isMax, char side, int key)
	{
		String kv = "";
		try 
		{
			//Determine if transformer is regulated
			String hasLTC = _hasLTC.getString(key);
			if(hasLTC.equals("1"))
			{
				//Determine which side has the regulation
				String hside = _hSide.getString(key);
				if((hside.equals("1") && side == 'f') || (hside.equals("0") && side == 't'))
				{
					kv = (isMax)? _maxKV.getString(key) : _minKV.getString(key);
				}
			}
		} 
		catch (PDDBException e) 
		{
			System.err.println("[RatioTapChangerOPAExtended] Issue trying to get KV for key "+key);
			e.printStackTrace();
		}
		
//		System.out.println("\nRatioTapChangerOPAExnteded]\nKey: "+key+"\nisMax: "+isMax+"\nKV: "+kv);
		return kv;
	}

	@Override
	void export(File outputdir) throws PAModelException, IOException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outputdir, getPsmFmtName()+".csv"))));
		printHeader(pw);
		printData(pw, _lfi, getCount());
		printData(pw);
		pw.close();
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.RatioTapChanger.toString();
	}

}
