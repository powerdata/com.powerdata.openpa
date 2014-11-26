package com.powerdata.openpa.psseraw;

import java.util.List;

import gnu.trove.map.TObjectIntMap;

public class PssePhaseShifterTool implements Psse2PsmEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _tfmrId;
	protected String _wdgId;
	protected String _phaseHighId;
	protected String _phaseLowId;
	protected String _name;
	protected int _wdgCount;
	protected String _node1;
	protected String _node2;
	protected String _r;
	protected String _x;
	protected String _bmag;
	protected String _normOpLimit;
	protected String _controlStatus;
	protected String _phaseHigh;
	protected String _phaseLow;
	
	public PssePhaseShifterTool(List<PsseField[]> lines, String[] record) 
	{
		if(_fldMap == null) _fldMap = buildMap(lines);
	
		_name 			= record[_fldMap.get("name")];
		_wdgCount 		= 2;
		_node1 			= record[_fldMap.get("i")];
		_node2 			= record[_fldMap.get("j")];
		_r				= record[_fldMap.get("r1-2")];
		_x				= record[_fldMap.get("x1-2")];
		_bmag 			= record[_fldMap.get("mag2")];
		_normOpLimit	= record[_fldMap.get("rata1")];
		_phaseHigh		= record[_fldMap.get("rma1")];
		_phaseLow		= record[_fldMap.get("rmi1")];

		Float ctrl 	= Float.parseFloat(record[_fldMap.get("cod1")]);
		_controlStatus	= (ctrl == Float.NaN || ctrl >= 0)?"true":"false";
		
		String idBase 	= _name+"_"+_node1+"_"+_node2;
		_tfmrId 		= idBase+"_tfmr";
		_wdgId 			= idBase+"_wdg";
		_phaseHighId	= idBase+"_ptc1";
		_phaseLowId		= idBase+"_ptc2";
	}

	public enum PhaseFiles
	{
		Transformer,
		TransformerWinding,
		PhaseTapChanger,
		PsmCasePhaseTapChanger
	}
	
	@Override
	public String toCsv(String type) 
	{
		return toCsv(PhaseFiles.valueOf(type));
	}
	
	public String toCsv(PhaseFiles file)
	{
		switch (file)
		{
		case PhaseTapChanger:
			String pHigh[] = {_phaseHighId,_node1,_wdgId};
			String pLow[] = {_phaseLowId,_node2,_wdgId};
			return arrayToCsv(pHigh)+"\n"+arrayToCsv(pLow);
		case PsmCasePhaseTapChanger:
			String pcHigh[] = {_phaseHighId,_controlStatus,_phaseHigh};
			String pcLow[] = {_phaseLowId,_controlStatus,_phaseLow};
			return arrayToCsv(pcHigh)+"\n"+arrayToCsv(pcLow);
		case Transformer:
			String t[] = {_tfmrId, _name, ""+_wdgCount};
			return arrayToCsv(t);
		case TransformerWinding:
			String w[] = {_wdgId,_name,_tfmrId,_node1,_node2,_r,_x,_bmag,_normOpLimit};
			return arrayToCsv(w);
		default:
			return "";
		}
	}
	
	public String getHeaders(PhaseFiles file)
	{
		switch (file)
		{
		case PhaseTapChanger:
			return "ID,TapNode,TransformerWinding";
		case PsmCasePhaseTapChanger:
			return "ID,ControlStatus,PhaseShift";
		case Transformer:
			return "ID,Name,WindingCount";
		case TransformerWinding:
			return "ID,Name,Transformer,Node1,Node2,R,X,Bmag,NormalOperatingLimit";
		default:
			return "";
		}
	}

	//Getters
	public String getTfmrId() { return _tfmrId; }
	public String getWdgId() { return _wdgId; }
	public String getPhaseHighId() { return _phaseHighId; }
	public String getPhaseLowId() { return _phaseLowId; }
	public String getName() { return _name; }
	public String getNode1() { return _node1; }
	public String getNode2() { return _node2; }
	public String getR() { return _r; }
	public String getX() { return _x; }
	public String getBmag() { return _bmag; }
	public String getNormalOpLimit() { return _normOpLimit; }
	public String getControlStatus() { return _controlStatus; }
	public String getPhaseHigh() { return _phaseHigh; }
	public String getPhaseLow() { return _phaseLow; }
	public static TObjectIntMap<String> getMap() { return _fldMap; }
}