package com.powerdata.openpa.psseraw;

import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.psseraw.PsseRepository.BusInfo;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import gnu.trove.map.TObjectIntMap;

public class PsseGenTool extends PsseEquipment 
{
	static String IdFmt = "%s-%s-%s";
	static String CrvFmt = "\"%s\",\"%s\",%s,%s,%s\n";
	
	int _i;
	int _id;
	int _pt;
	int _pb;
	int _pg;
	int _qg;
	int _ireg;
	int _vs;
	int _qb;
	int _qt;
	int _zr, _zx;
	int _stat;
	
	public PsseGenTool(PsseClass pc, PsseRepository rep) throws IOException
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		
		_id = fldMap.get("id");
		_pb = fldMap.get("pb");
		_pt = fldMap.get("pt");
		_pg = fldMap.get("pg");
		_i = fldMap.get("i");
		_ireg = fldMap.get("ireg");
		_qg = fldMap.get("qg");
		_qb = fldMap.get("qb");
		_qt = fldMap.get("qt");
		_vs = fldMap.get("vs");
		_zr = fldMap.get("zr");
		_zx = fldMap.get("zx");
		_stat = fldMap.get("stat");
	}

	static String mkGenId(String busi, String id)
	{
		return String.format(IdFmt, "gen", busi, id);
	}
	
	static String mkGenName(String busName, String id)
	{
		return String.format("%s-%s", busName, id);
	}
	
	static String mkSyncMachId(String busi, String id)
	{
		return String.format(IdFmt, "sm", busi, id);
	}
	
	static String mkSyncMachName(String busName, String id)
	{
		return String.format("%s-%s", busName, id);
	}

	static String mkCurveId(String busi, String id)
	{
		return String.format(IdFmt, "smvarcrv", busi, id);
	}
	
	@Override
	public void writeRecord(PsseClass pclass, String[] record) throws PsseProcException
	{
		String i = record[_i], id = record[_id];
		String genid = mkGenId(i, id);
		String smid = mkSyncMachId(i, id);
		BusInfo binfo = _rep.getBusInfo(i);
		String busname = binfo.getName();
		float vs = 1f;
		String svs = record[_vs];
		if (svs != null && !svs.isEmpty())
			vs = Float.parseFloat(svs);
		String insvc = String.valueOf(record[_stat].equals("1"));
		_rep.findWriter(PsmFormat.GeneratingUnit).format("\"%s\",\"%s\",%s,%s\n",
			genid, mkGenName(busname, id), record[_pb], record[_pt]);
		_rep.findWriter(CaseFormat.GeneratingUnit).format("\"%s\",%s,%s\n", 
			genid, record[_pg], insvc);
		
		String ireg = record[_ireg];
		
		_rep.findWriter(PsmFormat.SynchronousMachine).format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,%s\n",
			smid, mkSyncMachName(busname, id), i, genid, 
			ireg.equals("0")?i:ireg, record[_zr], record[_zx]);
		String qb = record[_qb], qt = record[_qt];
		String pb = record[_pb], pt = record[_pt];
		float fqg = getFloat(record[_qg]), fqt = getFloat(qt), fqb = getFloat(qb);
		boolean qsame = fqg == fqt && fqg == fqb;
		boolean avr = true, nocrv = false;
		if(qsame)
		{
			nocrv = true;
			if(fqg != 0f) avr = false;
		}

		_rep.findWriter(CaseFormat.SynchronousMachine).format("\"%s\",%s,%s,%s,%s,%s\n",
			smid, Boolean.toString(avr), vs * binfo.getBaskv(), avr ? "" : record[_qg],record[_qg], insvc);
		
		if (!nocrv)
		{
			PrintWriter crv = _rep.findWriter(PsmFormat.ReactiveCapabilityCurve);
			String crvid = mkCurveId(i, id);
			crv.format(CrvFmt, crvid, smid, pb, qb, qt);
			crv.format(CrvFmt, crvid, smid, pt, qb, qt);
		}
		
	}
	
}
