package com.powerdata.openpa.psseraw;

import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TObjectIntMap;

public class PsseSvcTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected List<String> _ids;
	protected List<String> _names;
	protected List<String> _nodes;
	protected List<String> _minMvar;
	protected List<String> _maxMvar;
	protected List<String> _slope;
	
	public PsseSvcTool(PsseField[] fld, String[] record) 
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_ids			= new ArrayList<>();
		_names			= new ArrayList<>();
		_nodes			= new ArrayList<>();
		_minMvar		= new ArrayList<>();
		_maxMvar		= new ArrayList<>();
		_slope			= new ArrayList<>();
		
		
		for(int i = 1; i < 9; ++i)
		{
			String b = record[_fldMap.get("b"+(i))];
			if(!b.equals(""))
			{
				_ids.add(b+"_"+record[_fldMap.get("i")]+"_svc");
				_names.add(b+"_"+record[_fldMap.get("i")]+"_svc");
				_nodes.add(record[_fldMap.get("i")]);
				_minMvar.add(record[_fldMap.get("vswlo")]);
				_maxMvar.add(record[_fldMap.get("vswhi")]);
				_slope.add("");
			}
		}
	}

	@Override
	public String toCsv(String type) 
	{
		return toCsv();
	}
	
	public String toCsv()
	{
		String[][] data = {(String[]) _ids.toArray(), 
							(String[]) _names.toArray(), 
							(String[]) _nodes.toArray(),
							(String[]) _minMvar.toArray(),
							(String[]) _maxMvar.toArray(),
							(String[]) _slope.toArray()
		};
		return arraysToCsv(data);
	}
	
	public String getHeaders()
	{
		return "ID,Name,Node,MinMVAr,MaxMVAr,Slope";
	}

}
