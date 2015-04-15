package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseLoadTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _id;
	protected String _name;
	protected String _node;
	protected String _mw;
	protected String _mvar;
	
	public PsseLoadTool(String i, String id, String pl, String ql)
	{
		_id = id+"_"+i+"_load";
		_name = id;
		_node = i;
		_mw = pl;
		_mvar = ql;
	}
	
	public PsseLoadTool(PsseField[] fld, String[] record)
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_id 	= record[_fldMap.get("id")]+"_"+record[_fldMap.get("i")]+"_load";
		_name 	= record[_fldMap.get("id")];
		_node 	= record[_fldMap.get("i")];
		_mw		= record[_fldMap.get("pl")];
		_mvar	= record[_fldMap.get("ql")];
	}
	
	public enum LoadFiles
	{
		Load,
		PsmCaseLoad
	}
	
	@Override
	public String toCsv(String type) 
	{ 
		return toCsv(LoadFiles.valueOf(type));
	}
	
	public String toCsv(LoadFiles file)
	{
		switch(file)
		{
		case Load:
			String[] l = {_id, _name, _node};
			return arrayToCsv(l);
		case PsmCaseLoad:
			String[] lc = {_id, _mw, _mvar};
			return arrayToCsv(lc);
		default:
			return null;
		}
	}
	
	public String getHeaders(LoadFiles file)
	{
		switch(file)
		{
		case Load:
			return "ID,Name,Node";
		case PsmCaseLoad:
			return "ID,MW,MVAr";
		default:
			return "";
		}
	}

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getNode() { return _node; }
	public String getMW() { return _mw; }
	public String getMVAr() { return _mvar;}

}
