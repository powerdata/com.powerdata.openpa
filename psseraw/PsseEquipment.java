package com.powerdata.openpa.psseraw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public abstract class PsseEquipment implements PsseRecWriter
{
	static final int _invalidOffset = -1;
	public static DecimalFormat _DFmt4 = new DecimalFormat("#.####");
	public static DecimalFormat _DFmt2 = new DecimalFormat("#.##");


	static public TObjectIntMap<String> buildMap(PsseField[] fld)
	{
		TObjectIntMap<String> map = new TObjectIntHashMap<>(fld.length, 1f, _invalidOffset);
		for(int i = 0; i < fld.length; ++i)
		{
			map.put(fld[i].getName().toLowerCase(), i);
		}
		
		return map;
	}
	
	static public TObjectIntMap<String> buildMap(List<PsseField[]> lines)
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
	
	public static PrintWriter openWriter(File dir, String fname) throws IOException
	{
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(dir,
				fname + ".csv"))));
	}

	public static float getFloat(String v) {return getFloat(v, 0f);}
	public static float getFloat(String v, float defval)
	{
		return (v == null || v.equals("")) ? defval : Float.parseFloat(v);
	}
	
	public static int getInt(String v) {return getInt(v, 0);}
	public static int getInt(String v, int defval)
	{
		return (v == null || v.equals("")) ? defval : Integer.parseInt(v);
	}
	
	public static boolean getBoolean(String v, boolean defval)
	{
		if (v == null || v.isEmpty()) return defval;
		char c = Character.toLowerCase(v.charAt(0));
		return c == '1' || c == 't';
	}

	
	protected PsseRepository _rep;

	public PsseEquipment(PsseRepository rep) {_rep = rep;}
	
}
