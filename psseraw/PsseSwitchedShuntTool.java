package com.powerdata.openpa.psseraw;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TObjectIntMap;

public class PsseSwitchedShuntTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	
	protected List<String> _reacIds;
	protected List<String> _reacNames;
	protected List<String> _reacNodes;
	protected List<String> _reacMvar;
	protected List<String> _capIds;
	protected List<String> _capNames;
	protected List<String> _capNodes;
	protected List<String> _capMvar;
	
	public PsseSwitchedShuntTool(PsseField[] fld, String[] record) 
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		_reacIds		= new ArrayList<>();
		_reacNames		= new ArrayList<>();
		_reacNodes		= new ArrayList<>();
		_reacMvar		= new ArrayList<>();
		_capIds			= new ArrayList<>();
		_capNames		= new ArrayList<>();
		_capNodes		= new ArrayList<>();
		_capMvar		= new ArrayList<>();
		
		//Maximum of 8 shunts per switched shunt
		for(int i = 1; i < 9; ++i)
		{
			//Determine if reactor or capacitor
			String bString = record[_fldMap.get("b"+(i))];
			if(!bString.equals(""))
			{
				Float b = Float.parseFloat(bString);
				if(b == Float.NaN) System.err.println("[PsseSwitchedShunt] Unable to conver b value \""+bString+"\" to a float");

				if(b < 0f)
				{
					createReactors(record, i);
				}
				else
				{
					createCapacitors(record, i);
				}
			}
		}
	}
	
	public enum ShuntFiles
	{
		ShuntReactor,
		ShuntCapacitor
	}
	
	private void createReactors(String[] record, int offset)
	{
		//Get count
		String id = offset+"_"+offset+"_"+record[_fldMap.get("i")]+"_shunt";
		
		_reacIds.add(id);//ID
		_reacNames.add(id);//Name
		_reacNodes.add(record[_fldMap.get("i")]);//Node
		_reacMvar.add(record[_fldMap.get("b"+offset)]);//MVAr
	}
	
	private void createCapacitors(String[] record, int offset)
	{
		//Get count
		String id = offset+"_"+offset+"_"+record[_fldMap.get("i")]+"_shunt";
		
		_capIds.add(id);//ID
		_capNames.add(id);//Name
		_capNodes.add(record[_fldMap.get("i")]);//Node
		_capMvar.add(record[_fldMap.get("b"+offset)]);//MVAr
	}
	
	@Override
	public String toCsv(String type) 
	{
		return toCsv(ShuntFiles.valueOf(type));
	}
	
	public String toCsv(ShuntFiles file)
	{
		switch (file)
		{
		case ShuntCapacitor:
			return createCsv(false);
		case ShuntReactor:
			return createCsv(true);
		default:
			return null;
		}
	}
	
	private String createCsv(boolean isReac)
	{
		String[][] data = new String[4][];
		
		if(isReac)
		{
			data[0] = (String[]) _reacIds.toArray();
			data[1] = (String[]) _reacNames.toArray();
			data[2] = (String[]) _reacNodes.toArray();
			data[3] = (String[]) _reacMvar.toArray();
		}
		else
		{
			data[0] = (String[]) _capIds.toArray();
			data[1] = (String[]) _capNames.toArray();
			data[2] = (String[]) _capNodes.toArray();
			data[3] = (String[]) _capMvar.toArray();
		}
		return arraysToCsv(data);
	}
	
	public String getHeaders()
	{
		return "ID,Name,Node,MVAr";
	}
	
	public enum ShuntType
	{
		SwitchedShunt,
		SVC
	}
	
	public static ShuntType getShuntType(PsseField[] fld, String[] record)
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(fld);
		
		String modsw = record[_fldMap.get("modsw")];
		
		return (modsw.equals("3"))? ShuntType.SVC : ShuntType.SwitchedShunt;
	}
}
