package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;

public class PsseLineTool implements Psse2PsmEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected String _id;
	protected String _name;
	protected String _node1;
	protected String _node2;
	protected String _r;
	protected String _x;
	protected String _bch;
	protected String _length;

	public PsseLineTool(PsseField[] fld, String[] record) 
	{
		// TODO Auto-generated constructor stub
		//Based on PSS/E version 30
		if(_fldMap == null) _fldMap = buildMap(fld);
		_id 	= record[_fldMap.get("id")]+"_"+record[_fldMap.get("i")]+"_"+record[_fldMap.get("j")]+"_line";
		_name 	= record[_fldMap.get("id")];
		_node1 	= record[_fldMap.get("i")];
		_node2 	= record[_fldMap.get("j")];
		_r		= record[_fldMap.get("r")];
		_x		= record[_fldMap.get("x")];
		_bch	= record[_fldMap.get("b")];
		_length = record[_fldMap.get("len")];
	}

	@Override
	public String toCsv(String file) 
	{
		String l[] = {_id,_name,_node1,_node2,_r,_x,_bch,_length};
		return arrayToCsv(l);
	}
	
	public String getHeaders()
	{
		return "ID,Name,Node1,Node2,R,X,Bch,Length";
	}

	//Getters
	public String getId() { return _id; }
	public String getName() { return _name; }
	public String getNode1() { return _node1; }
	public String getNode2() { return _node2; }
	public String getR() { return _r; }
	public String getX() { return _x; }
	public String getBch() { return _bch; }
	public String getLength() { return _length; }
	
}
