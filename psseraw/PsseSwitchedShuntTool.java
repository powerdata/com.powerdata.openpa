package com.powerdata.openpa.psseraw;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import com.powerdata.openpa.psseraw.PsseRepository.BusInfo;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import gnu.trove.map.TObjectIntMap;

public class PsseSwitchedShuntTool extends PsseEquipment 
{
	private static int MAXSHUNTPERLINE = 8;
	
	private int _i, _modsw, _vswhi, _vswlo, _swrem, _binit;
	private int[] _n, _b;
	
	Map<String,Integer> _shuntNumber = new HashMap<>();
	
	enum PsseMODSW
	{
		Fixed, Discrete, Continuous, DiscretePlantControl, DiscreteVscControl, DiscreteShuntControl;
		public static PsseMODSW getByCode(int code)
		{
			PsseMODSW[] v = PsseMODSW.values();
			return (code < v.length) ? v[code] : PsseMODSW.Fixed; 
		}
	}
	
	public PsseSwitchedShuntTool(PsseClass pc, PsseRepository rep)
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_modsw = fldMap.get("modsw");
		_vswhi = fldMap.get("vswhi");
		_vswlo = fldMap.get("vswlo");
		_swrem = fldMap.get("swrem");
		_binit = fldMap.get("binit");
		
		_n = new int[MAXSHUNTPERLINE];
		_b = new int[MAXSHUNTPERLINE];
		
		for(int i=0, j=1; i < MAXSHUNTPERLINE; ++i, ++j)
		{
			_n[i] = fldMap.get("n"+j);
			_b[i] = fldMap.get("b"+j);
		}
	}

	@Override
	public void writeRecord(PsseClass pclass, String[] record) throws PsseProcException
	{
		PsseMODSW modsw = PsseMODSW.getByCode(getInt(record[_modsw], 1));
		String busid = PsseBusTool.mkId(record[_i]);
		int shuntNumber = _shuntNumber.getOrDefault(busid, 0) + 1;
		BusInfo binfo = _rep.getBusInfo(busid);
		float vswhi = binfo.getBaskv() * getFloat(record[_vswhi], 1f);
		float vswlo = binfo.getBaskv() * getFloat(record[_vswlo], 1f);
		String swrem = PsseBusTool.mkId(record[_swrem]);
		if (swrem.isEmpty() || swrem.equals("0"))
			swrem = busid;
		float binit = getFloat(record[_binit],0f);
		
		for(int i=0; i < MAXSHUNTPERLINE; ++i)
		{
			int n = getInt(record[_n[i]],0);
			float b = getFloat(record[_b[i]], 0f);
			
			while (--n >= 0)
			{
				String shid = makeId(busid, shuntNumber);
				PsmFormat pf;
				CaseFormat cf;
				if (b <= 0f)
				{
					pf = PsmFormat.ShuntReactor;
					cf = CaseFormat.ShuntReactor;
				}
				else
				{
					pf = PsmFormat.ShuntCapacitor;
					cf = CaseFormat.ShuntCapacitor;
				}
				
				PrintWriter pw = _rep.findWriter(pf);
				PrintWriter cw = _rep.findWriter(cf);
				
				boolean unsupported = modsw.ordinal() > PsseMODSW.Continuous.ordinal();
				if(unsupported)
				{
					System.err.format("MODSW=%s not supported, changing to fixed at bus %s\n", 
						modsw.toString(), busid);
				}
				
				pw.format("\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",%s,%s\n",
					shid, 
					makeName(busid, shuntNumber),
					busid,
					_DFmt2.format(b),
					Boolean.toString(!unsupported),
					swrem,
					unsupported ? "" : _DFmt2.format(vswlo),
					unsupported ? "" : _DFmt2.format(vswhi));
				
				cw.format("\"%s\",%s,%s\n",
					shid, 
					Boolean.toString(modsw != PsseMODSW.Fixed),
					Boolean.toString(binit != 0f));
					
				binit -= b;
				++shuntNumber;
			}
		}
		_shuntNumber.put(busid, shuntNumber);
	}

	String makeName(String busid, int shuntNumber)
	{
		return String.format("%s-%d", 
			_rep.getBusName(busid), shuntNumber);
	}

	private String makeId(String busid, int shuntNumber)
	{
		return String.format("swsh-%s-%d", busid, shuntNumber);
	}
	
}
