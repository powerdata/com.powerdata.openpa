package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseOwnerTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	
	protected String _id;
	protected String _name;
	
	public PsseOwnerTool(PsseField[] fld, String[] record) 
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_id = record[_fldMap.get("i")]+"_org";
		_name = record[_fldMap.get("owname")];
	}

	@Override
	public String toCsv(String type) 
	{
		return toCSV();
	}
	
	public String toCSV()
	{ 
		return _id+","+_name;
	}
	
	public String getHeaders()
	{
		return "ID,Name";
	}

}
