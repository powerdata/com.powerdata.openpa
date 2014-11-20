package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseAreaTool implements Psse2PsmEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _id;
	protected String _name;
	
	public PsseAreaTool(PsseField[] fld, String[] record) 
	{
		if(_fldMap == null) _fldMap = buildMap(fld);
		
		_id = record[_fldMap.get("i")]+"_ca";
		_name = record[_fldMap.get("arname")];
	}

	@Override
	public String toCsv(String type) 
	{
		return toCsv();
	}
	
	public String toCsv() 
	{
		String[] a = {_id, _name};
		return arrayToCsv(a);
	}
	
	public String getHeaders()
	{
		return "ID,Name";
	}

}
