package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;
import java.io.IOException;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;

public class PsseLoadTool extends PsseEquipment 
{
	int _i, _id, _mw, _mvar, _status;

	public PsseLoadTool(PsseClass pc, PsseRepository rep) throws IOException
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_id = fldMap.get("id");
		_mw = fldMap.get("pl");
		_mvar = fldMap.get("ql");
		_status = fldMap.get("status");
	}
	
	@Override
	public void writeRecord(PsseClass pclass, String[] record) throws PsseProcException
	{
		String id = mkId(record[_i], record[_id]);
		_rep.findWriter(PsmFormat.Load).format("\"%s\",\"%s\",\"%s\"\n",
			id, mkname(record), record[_i]);
		_rep.findWriter(CaseFormat.Load).format("\"%s\",%s,%s,%s\n",
			id, record[_mw], record[_mvar], Boolean.toString(getBoolean(record[_status], true)));
	}

	private String mkname(String[] record)
	{
		return String.format("%s-%s", _rep.getBusName(record[_i]), record[_id]);
	}


	public static String mkId(String psseBus, String psseId)
	{
		return String.format("load-%s-%s", psseBus, psseId);
	}

}
