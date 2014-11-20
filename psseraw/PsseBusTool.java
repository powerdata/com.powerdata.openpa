package com.powerdata.openpa.psseraw;

public class PsseBusTool implements Psse2PsmEquipment 
{
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

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getBasekv() { return _baskv; }
	public String getOwner() { return _owner; }
}
