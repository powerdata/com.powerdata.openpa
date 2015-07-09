package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseBusTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _id;
	protected String _name;
	protected String _baskv;
	protected String _owner;
	protected String _area;
	
	public PsseBusTool(String i, String name, String baskv, String area, String owner)
	{
		_id = i;
		_name = name;
		_baskv = baskv;
		_owner = owner;
		_area = area;
	}
	
	public PsseBusTool(PsseField[] fld, String[] record)
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_id 	= record[_fldMap.get("i")];
		_name	= record[_fldMap.get("name")];
		_baskv 	= record[_fldMap.get("baskv")];
		_owner	= record[_fldMap.get("owner")];
		_area   = record[_fldMap.get("area")];
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
			_area 	= record[_fldMap.get("area")];
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
		return String.format("%s,%s,%s,%s_ca,%s_org",
			_id, _name, _baskv, _area, _owner);
	}
	
	public String getHeaders()
	{
		return "ID,Name,NominalKV,Owner,Area";
	}

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getBasekv() { return _baskv; }
	public String getOwner() { return _owner; }
	public String getArea() {return _area;}
	public static TObjectIntMap<String> getMap() { return _fldMap; }
}
