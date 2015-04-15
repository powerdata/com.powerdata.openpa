package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseBusTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _id;
	protected String _name;
	protected String _baskv;
	protected String _owner;
	
	public PsseBusTool(String i, String name, String baskv, String owner)
	{
		_id = i;
		_name = name;
		_baskv = baskv;
		_owner = owner;
	}
	
	public PsseBusTool(PsseField[] fld, String[] record)
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_id 	= record[_fldMap.get("i")];
		_name	= record[_fldMap.get("name")];
		_baskv 	= record[_fldMap.get("baskv")];
		_owner	= record[_fldMap.get("owner")];
	}
	
	public PsseBusTool(String[] record)
	{
		if(_fldMap == null) 
		{
			System.err.println("[PsseBusTool.java] Cannot create bus. No field map found.");
		}
		else
		{
			_id 	= record[_fldMap.get("i")];
			_name	= record[_fldMap.get("name")];
			_baskv 	= record[_fldMap.get("baskv")];
			_owner	= record[_fldMap.get("owner")];
		}
	}
	
	public String toCsv()
	{
		return toCsv("");
	}
	
	@Override
	public String toCsv(String type) 
	{
		// ID,Name,NominalKV,Substation,FrequencySourcePriority
		return _id+","+_name+","+_baskv;
	}
	
	public String getHeaders()
	{
		return "ID,Name,NominalKV";
	}

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getBasekv() { return _baskv; }
	public String getOwner() { return _owner; }
	public static TObjectIntMap<String> getMap() { return _fldMap; }
}
