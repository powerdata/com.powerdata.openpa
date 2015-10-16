package com.powerdata.openpa.psseraw;

import java.io.IOException;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import gnu.trove.map.TObjectIntMap;

public class PsseOwnerTool extends PsseEquipment 
{
	int _i, _namex;
	
	public PsseOwnerTool(PsseClass pc, PsseRepository rep) throws IOException 
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_namex = fldMap.get("owname");
	}

	static public String mkId(String psseId)
	{
		return new StringBuilder(psseId).append("_org").toString();
	}

	@Override
	public void writeRecord(PsseClass pclass, String[] record)
		throws PsseProcException
	{
		_rep.findWriter(PsmFormat.Organization).format(
			"\"%s\",\"%s\"\n", mkId(record[_i]), record[_namex]);
	}

}
