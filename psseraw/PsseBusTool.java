package com.powerdata.openpa.psseraw;

import java.io.IOException;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import gnu.trove.map.TObjectIntMap;

public class PsseBusTool extends PsseEquipment 
{
	static String _ShuntFmt = "\"%s\",\"%s\",\"%s\",%s,false,,,\n";

	int _i;
	int _name;
	int _baskv;
	int _owner;
	int _area;
	int _zone;
	int _vm, _va, _bl;
	
	public PsseBusTool(PsseClass pc, PsseRepository rep) throws IOException
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_name = fldMap.get("name");
		_baskv = fldMap.get("baskv");
		_owner = fldMap.get("owner");
		_area = fldMap.get("area");
		_zone = fldMap.get("zone");
		_vm = fldMap.get("vm");
		_va = fldMap.get("va");
		_bl = fldMap.get("bl");
		
	}

	@Override
	public void writeRecord(PsseClass pclass, String[] record) 
		throws PsseProcException
	{
		String i = record[_i];
		String id = mkId(i);
		String name = record[_name];
		String ownid = PsseOwnerTool.mkId(record[_owner]);
		String areaid = PsseAreaTool.mkId(record[_area]);
		float baskv = getFloat(record[_baskv]);
		_rep.findWriter(PsmFormat.Node).format("\"%s\",\"%s\",%s,\"%s\",\"%s\",true\n",
			id, name, record[_baskv],
			ownid,
			areaid);
		_rep.findWriter(CaseFormat.Node).format("\"%s\",%s,%f\n",
			id, record[_va], getFloat(record[_vm])*baskv);
		_rep.mapBusInfo(id, name, baskv, ownid, areaid);
		
		float bl = getFloat(record[_bl]);
		String shid = mkShuntId(i), shname = mkShuntName(i);
		if(bl < 0f)
		{
			_rep.findWriter(PsmFormat.ShuntReactor).format(_ShuntFmt,
				shid, shname, i, _DFmt4.format(bl));
			_rep.findWriter(CaseFormat.ShuntReactor).format("\"%s\",,true\n", 
				shid);
		}
		else if(bl > 0f)
		{
			_rep.findWriter(PsmFormat.ShuntCapacitor).format(_ShuntFmt,
				shid, shname, i, _DFmt4.format(bl));
			_rep.findWriter(CaseFormat.ShuntCapacitor).format("\"%s\",,true\n", 
				shid);
		}
	}
	
	private String mkShuntName(String i)
	{
		return i+"_bsh";
	}

	private String mkShuntId(String i)
	{
		return i+"_bsh";
	}

	public static String mkId(String i)
	{
		return i;
	}
	
}
