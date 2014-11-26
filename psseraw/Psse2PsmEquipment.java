package com.powerdata.openpa.psseraw;

import java.util.List;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public interface Psse2PsmEquipment 
{
	static final int _invalidOffset = -1;
	
	public String toCsv(String type);
	default String arrayToCsv(String[] data)
	{
		String csv = "";
		for(String d : data) { csv += d+","; }
		csv = csv.substring(0, csv.length()-1);
		return csv;
	}
	default TObjectIntMap<String> buildMap(PsseField[] fld)
	{
		TObjectIntMap<String> map = new TObjectIntHashMap<>(fld.length, 1f, _invalidOffset);
		for(int i = 0; i < fld.length; ++i)
		{
			map.put(fld[i].getName().toLowerCase(), i);
		}
		
		return map;
	}
	
	default TObjectIntMap<String> buildMap(List<PsseField[]> lines)
	{
		int size = 0;
		int lSize = lines.size();
		
		//Determine the size of the map
		for(int i = 0; i < lSize; ++i)
		{
			size += lines.get(i).length;
		}
		
		TObjectIntMap<String> map = new TObjectIntHashMap<>(size, 1f, _invalidOffset);
		int offset = 0;
		
		for(int i = 0; i < lSize; ++i)
		{
			PsseField[] fld = lines.get(i);
			for(int j = 0; j < fld.length; ++j)
			{
				map.put(fld[j].getName().toLowerCase(), offset);
				offset++;
			}
		}
		
		return map;
	}
}
