package com.powerdata.openpa.psseraw;

import java.util.List;

import gnu.trove.map.TObjectIntMap;

public class PsseTransformerTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _tfmrId;
	protected String _wdgId;
	protected String _ratioHighId;
	protected String _ratioLowId;
	protected String _name;
	protected String _node1;
	protected String _node2;
	protected String _r;
	protected String _x;
	protected String _bmag;
	protected String _normOpLimit;
	protected String _ratioHigh;
	protected String _ratioLow;
	protected int _wdgCount;
	protected String _nomv1, _nomv2;
	
	
	
	public PsseTransformerTool(List<PsseField[]> lines, String[] record)
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(lines);
		
		_name 			= record[_fldMap.get("name")];
		_node1 			= record[_fldMap.get("i")];
		_node2 			= record[_fldMap.get("j")];
		_r				= record[_fldMap.get("r1-2")];
		_x				= record[_fldMap.get("x1-2")];
		_bmag 			= record[_fldMap.get("mag2")];
		_normOpLimit	= record[_fldMap.get("rata1")];
		_ratioHigh		= record[_fldMap.get("windv1")];
		_ratioLow		= record[_fldMap.get("windv2")];
		_nomv1			= record[_fldMap.get("nomv1")];
		_nomv2			= record[_fldMap.get("nomv2")];
		_wdgCount 		= 2;
	
		String idBase 	= _name+"_"+_node1+"_"+_node2;
		_tfmrId 		= idBase+"_tfmr";
		_wdgId 			= idBase+"_wdg";
		_ratioHighId	= idBase+"_ftap";
		_ratioLowId		= idBase+"_ttap";
	}

	public enum TfmrFiles
	{
		Transformer,
		TransformerWinding,
		RatioTapChanger,
		PsmCaseRatioTapChanger
	}
	
	@Override
	public String toCsv(String type) 
	{
		return toCsv(TfmrFiles.valueOf(type));
	}
	
	public String toCsv(TfmrFiles file)
	{
		switch(file)
		{
		case PsmCaseRatioTapChanger:
			String rcHigh[] = {_ratioHighId, _ratioHigh};
			String rcLow[] = {_ratioLowId, _ratioLow};
			return arrayToCsv(rcHigh)+"\n"+arrayToCsv(rcLow);
		case RatioTapChanger:
			String rHigh[] = {_ratioHighId, _wdgId, _node1};
			String rLow[] = {_ratioLowId, _wdgId, _node2};
			return arrayToCsv(rHigh)+"\n"+arrayToCsv(rLow);
		case Transformer:
			String t[] = {_tfmrId, _name, ""+_wdgCount};
			return arrayToCsv(t);
		case TransformerWinding:
			String w[] = {_wdgId,_name,_tfmrId,_node1,_node2,_r,_x,_bmag,_normOpLimit,_nomv1,_nomv2};
			return arrayToCsv(w);
		default:
			return "";
		}
	}
	
	public String getHeaders(TfmrFiles file)
	{
		switch(file)
		{
		case PsmCaseRatioTapChanger:
			return "ID,Ratio";
		case RatioTapChanger:
			return "ID,TransformerWinding,TapNode";
		case Transformer:
			return "ID,Name,WindingCount";
		case TransformerWinding:
			return "ID,Name,Transformer,Node1,Node2,R,X,Bmag,NormalOperatingLimit,NeutralKV1,NeutralKV2";
		default:
			return "";
		}
	}
	

	public static TfmrType getTfmrType(List<PsseField[]> lines, String[] record) 
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(lines);
		
		int k = Integer.parseInt(record[_fldMap.get("k")]);
		String cod1 = record[_fldMap.get("cod1")];
		
		if(k == 0 || k == _fldMap.getNoEntryValue())
		{
			if(cod1.equals("3"))
			{
				return TfmrType.PhaseShifter;
			}
			else
			{
				return TfmrType.TwoWinding;
			}
		}
		
		return TfmrType.ThreeWinding;
	}

	public enum TfmrType
	{
		TwoWinding,
		ThreeWinding,
		PhaseShifter
	}
	
	//Getters
	public String getTfmrId() { return _tfmrId; }
	public String getWdgId() { return _wdgId; }
	public String getRatioHighId() { return _ratioHighId; }
	public String getRatioLowId() { return _ratioLowId; }
	public String getName() { return _name; }
	public String getNode1() { return _node1; }
	public String getNode2() { return _node2; }
	public String getR() { return _r; }
	public String getX() { return _x; }
	public String getBmag() { return _bmag; }
	public String getNormalOpLimit() { return _normOpLimit; }
	public String getRatioHigh() { return _ratioHigh; }
	public String getRatioLow() { return _ratioLow; }
	public int getWdgCount() { return _wdgCount; }
	public static TObjectIntMap<String> getMap() { return _fldMap; }
}
