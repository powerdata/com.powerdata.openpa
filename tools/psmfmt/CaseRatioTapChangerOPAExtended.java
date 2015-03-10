package com.powerdata.openpa.tools.psmfmt;

import java.util.Arrays;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.pd3.Column;
import com.powerdata.pd3.PDDB;
import com.powerdata.pd3.PDDBException;
import com.powerdata.pd3.View;

// Bandaid fix needed for creating SES files
// OpenPA does not currently support all the required data that the CSVs used for generating SES files need
// This class will take a PDDB that it will use to pull the missing data from
public class CaseRatioTapChangerOPAExtended extends CaseRatioTapChangerOPA 
{
	Column _ltcCol;
	Column _ftapCol;
	Column _ttapCol;
	Column _hsideCol;
	
	public CaseRatioTapChangerOPAExtended(PAModel m, PDDB db) throws PAModelException, PDDBException 
	{
		super(m);
		
		//LTC data is contained in Xfr view
		View xfrView = db.openView("view:group=pd3openpa&name=Xfr&ctx=Ots&inputctx=OtsControl", null);
//		_ltcCol 	= xfrView.col("hasLTC");
		_ltcCol 	= xfrView.col("ENABL");
		_ftapCol 	= xfrView.col("fTap");
		_ttapCol 	= xfrView.col("tTap");
		_hsideCol 	= xfrView.col("hside");
	}
	
	@Override
	void assignFrom()
	{
		super.assignFrom();
		//LTC Value is found in
		assign(CaseRatioTapChanger.LTCEnable, 
				i -> ltcValue(_list.getKey(i), true));
		assign(CaseRatioTapChanger.TapPosition,
				i -> tapPos(true, _list.getKey(i)));
	}
	
	@Override
	void assignTo()
	{
		super.assignTo();
		assign(CaseRatioTapChanger.LTCEnable, 
				i -> ltcValue(_list.getKey(i), false));
		assign(CaseRatioTapChanger.TapPosition,
				i -> tapPos(false, _list.getKey(i)));
	}

	String ltcValue(int key, boolean isHside)
	{
		String ltc = "False";
		try 
		{
			boolean hside = (_hsideCol.getLong(key) == 1);
			if( isHside == hside )
			{
				ltc = (_ltcCol.getLong(key) == 1)?"True":"False";
			}
		} 
		catch (PDDBException e) 
		{ 
			System.err.println("[CaseRatioTapChangerExtended] Issue fetching hasLTC value with key "+key);
			e.printStackTrace();
		}
		
		return ltc;
	}
	
	String tapPos(boolean isFtap, int key)
	{
		long pos = 0;
		
		try 
		{
			pos = (isFtap)? _ftapCol.getLong(key) : _ttapCol.getLong(key);
		}
		catch (PDDBException e) 
		{
			System.err.println("[CaseRatioTapChangerExtended] Issue fetching tap position with key "+key);
			e.printStackTrace();
		}
		
		return ""+pos;
	}
}
