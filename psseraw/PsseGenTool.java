package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseGenTool implements Psse2PsmEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _genId;
	protected String _synchId;
	protected String _curveId;
	protected String _name;
	protected String _maxOpMw;
	protected String _minOpMw;
	protected String _unitType;
	protected String _ctrlMode;
	protected String _mw;
	protected String _mvar;
	protected String _mwSetPoint;
	protected String _node;
	protected String _regNode;
	protected String _synchOpMode;
	protected String _avrMode;
	protected String _kvSetPoint;
	protected String _mvarSetPoint;
	protected String _minMvar;
	protected String _maxMvar;
	
	public PsseGenTool(PsseField[] fld, String[] record)
	{
		if(_fldMap == null) _fldMap = buildMap(fld);
		
		String baseId = record[_fldMap.get("id")]+"_"+record[_fldMap.get("i")];
		_genId = baseId+"_gu";
		_synchId = baseId+"_sm";
		_curveId = baseId+"_crv2";
		
		_name = record[_fldMap.get("id")];
		_minOpMw = record[_fldMap.get("pb")];
		_maxOpMw = record[_fldMap.get("pt")];
		_unitType = findUnitType();
		_ctrlMode = "Setpoint";
		_mw = record[_fldMap.get("pg")];
		_mwSetPoint = record[_fldMap.get("pg")];
		_node = record[_fldMap.get("i")];
		_regNode = record[_fldMap.get("ireg")];
		_synchOpMode = findSynchOpMode();
		_mvar = record[_fldMap.get("qg")];
		_minMvar = record[_fldMap.get("qb")];
		_maxMvar = record[_fldMap.get("qt")];
		
		if(_maxMvar.equals(_mvar) && _maxMvar.equals(_minMvar))
		{
			_avrMode = "OFF";
			_kvSetPoint = record[_fldMap.get("vs")];
			_mvarSetPoint = _kvSetPoint;
		}
		else
		{
			_avrMode = "ON";
			_kvSetPoint = record[_fldMap.get("vs")];
			_mvarSetPoint = "";
		}
	}
	
	public enum GenFiles
	{
		GeneratingUnit,
		PsmCaseGeneratingUnit,
		SynchronousMachine,
		PsmCaseSynchronousMachine,
		ReactiveCapabilityCurve
	}
	
	public String findUnitType()
	{
		String type;
		int pb = Integer.parseInt(_minOpMw);
		int pt = Integer.parseInt(_maxOpMw);
		if(pb < 0)
		{
			type = (!(pt < 1 && pb > -1))?"Hydro":"Thermal";
		}
		else
		{
			type = "Thermal";
		}
		
		return type;
	}
	public String findSynchOpMode()
	{
		float pt = Float.parseFloat(_maxOpMw);
		float pb = Float.parseFloat(_minOpMw);
		float pg = Float.parseFloat(_mwSetPoint);
		
		if(pt < 1 && pb > -1)
		{
			return "CON";
		}
		else if(pb >= 0 && pt >= pb)
		{
			return "GEN";
		}
		else if(pb < 0 && pt > 0 && pg < 0)
		{
			return "PMP";
		}
		else
		{
			return "";
		}
	}
	
	
	@Override
	public String toCsv(String file) 
	{
		return toCsv(GenFiles.valueOf("type"));
	}
	
	public String toCsv(GenFiles file)
	{
		switch(file)
		{
		case GeneratingUnit:
			String g[] = {_genId, _name, _minOpMw, _maxOpMw, _unitType, _ctrlMode};
			return arrayToCsv(g);
		case PsmCaseGeneratingUnit:
			String cg[] = {_genId, _mw, _mwSetPoint};
			return arrayToCsv(cg);
		case SynchronousMachine:
			String s[] = {_synchId, _name, _node, _genId, _regNode};
			return arrayToCsv(s);
		case PsmCaseSynchronousMachine:
			String cs[] = {_synchId, _avrMode, _kvSetPoint, _mvarSetPoint, _mvar};
			return arrayToCsv(cs);
		case ReactiveCapabilityCurve:
			String r[] = {_curveId, _synchId, _mw, _minMvar, _maxMvar};
			return arrayToCsv(r);
		default:
			return null;
		}
	}
	
	public String getHeaders(GenFiles file)
	{
		switch(file)
		{
		case GeneratingUnit:
			return "ID,Name,MinOperatingMW,MaxOperatingMW,GeneratingUnitType,GenControlMode";
		case PsmCaseGeneratingUnit:
			return "ID,MW,MWSetPoint,GeneratorOperatingMode";
		case PsmCaseSynchronousMachine:
			return "ID,Name,Node,GeneratingUnit,RegulatedNode";
		case ReactiveCapabilityCurve:
			return "ID,SynchronousMachingOperatingMode,AVRMode,KVSetPoint,MVArSetpoint,MVAr";
		case SynchronousMachine:
			return "ID,SynchronousMachine,MW,MinMVAr,MaxMVAr";
		default:
			return "";
		}
	}

	// Getters
	public String getGenId() { return _genId; }
	public String getSynchId() { return _synchId; }
	public String getCurveId() { return _curveId; }
	public String getName() { return _name; }
	public String getMaxOperatingMw() { return _maxOpMw; }
	public String getMinOperatingMw() { return _minOpMw; }
	public String getGenUnitType() { return _unitType; }
	public String getGenControlMode() { return _ctrlMode; }
	public String getMw() { return _mw; }
	public String getMvar() { return _mvar; }
	public String getMwSetPoint() { return _mwSetPoint; }
	public String getNode() { return _node; }
	public String getRegulatingNode() { return _regNode; }
	public String getSynchronousOpMode() { return _synchOpMode; }
	public String getAvrMode() { return _avrMode; }
	public String getKvSetPoint() { return _kvSetPoint; }
	public String getMvarSetPoint() { return _mvarSetPoint; }
	public String getMinMvar() { return _minMvar; }
	public String getMaxMvar() { return _maxMvar; }
}
