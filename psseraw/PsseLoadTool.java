package com.powerdata.openpa.psseraw;

public class PsseLoadTool implements Psse2PsmEquipment 
{
	
	
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
		
	}
	
	@Override
	public String toCsv(String type) 
	{ 
		switch(type.toLowerCase())
		{
		case "load":
			String[] l = {_id, _name, _node};
			return arrayToCsv(l);
		case "psmcaseload":
			String[] lc = {_id, _mw, _mvar};
			return arrayToCsv(lc);
		default:
			return null;
		}
	}

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getNode() { return _node; }
	public String getMW() { return _mw; }
	public String getMVAr() { return _mvar;}

}
