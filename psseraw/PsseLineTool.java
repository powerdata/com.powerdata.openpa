package com.powerdata.openpa.psseraw;

import java.util.Arrays;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import gnu.trove.map.TObjectIntMap;

public class PsseLineTool extends PsseEquipment 
{
	static String _ShuntFmt = "\"%s\",\"%s\",\"%s\",%s,false,,,\n";

	private int _i;
	private int _j;
	private int _ckt;
	private int _r;
	private int _x;
	private int _b;
	private int _ratea, _rateb, _ratec;
	private int _bi, _bj;
	private int _st;
	
	public PsseLineTool(PsseClass pc, PsseRepository rep) 
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_j = fldMap.get("j");
		_ckt = fldMap.get("ckt");
		_r = fldMap.get("r");
		_x = fldMap.get("x");
		_b = fldMap.get("b");
		_ratea = fldMap.get("ratea");
		_rateb = fldMap.get("rateb");
		_ratec = fldMap.get("ratec");
		_bi = fldMap.get("bi");
		_bj = fldMap.get("bj");
		_st = fldMap.get("st");
	}



	@Override
	public void writeRecord(PsseClass pclass, String[] record) throws PsseProcException
	{
		String i = record[_i], j = record[_j], ckt = record[_ckt];
		if (j.charAt(0) == '-') j = j.substring(1);
		
		float[] ratings = new float[]
		{
			getFloat(record[_ratea]),
			getFloat(record[_rateb]),
			getFloat(record[_ratec])
		};
		Arrays.sort(ratings);
		String name = String.format("%s-%s", _rep.getBusName(i), _rep.getBusName(j));
		String id = String.format("ln-%s-%s-%s", i, j, ckt); 
		
		_rep.findWriter(PsmFormat.Line).format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,%s,%s,%s,%s,%s\n",
			id,
			name,
			ckt, i, j,
			record[_r], record[_x], record[_b],
			_DFmt2.format(ratings[0]),
			_DFmt2.format(ratings[1]),
			_DFmt2.format(ratings[2]));
		_rep.findWriter(CaseFormat.Line).format("\"%s\",%s\n", id, String.valueOf(record[_st].equals("1")));
		
		addShunt(i, id, name, "fsh", record[_bi]);
		addShunt(j, id, name, "tsh", record[_bj]);
	}



	void addShunt(String busid, String id, String name, String prefix, String sb)
	{
		float b = getFloat(sb);
		if (b < 0f)
		{
			addShunt(mkShuntId(id, prefix), mkShuntName(name, prefix), busid, b*100f,
				PsmFormat.ShuntReactor, CaseFormat.ShuntReactor);
		}
		else if (b > 0f)
			addShunt(mkShuntId(id, prefix), mkShuntName(name, prefix), busid, b*100f,
				PsmFormat.ShuntCapacitor, CaseFormat.ShuntCapacitor);
	}
		
	private String mkShuntName(String linename, String prefix)
	{
		return String.format("%s-%s", linename, prefix);
	}

	void addShunt(String id, String name, String busid, float b, PsmFormat fmt, CaseFormat cf)
	{
		_rep.findWriter(fmt).format(_ShuntFmt, 
			id, name, busid, _DFmt4.format(b));
		_rep.findWriter(cf).format("\"%s\",,true\n", id);
	}
	
	String mkShuntId(String id, String prefix)
	{
		return String.format("%s-%s", id, prefix);
	}

}
