package com.powerdata.openpa.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoadArray
{
	public static int[] LoadInts(String vals[],int size, Object def, String fn)
		throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		int array[] = new int[size];
		Method getDef = def.getClass().getMethod(fn, int.class);
		// if no vals were provided use all defaults
		if (vals == null)
		{
			for(int i=0; i<size; i++)
			{
				array[i] = (int)getDef.invoke(def, i);
			}
		}
		else
		{
			for(int i=0; i<size; i++)
			{
				String v = vals[i];
				array[i] = (v != null && SimpleCSV.IsNumber(v))?
					Integer.parseInt(v):(int)getDef.invoke(def, i);
			}
		}
		return array;
	}
	public static float[] LoadFloats(String vals[],int size, Object def, String fn)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
		{
			float array[] = new float[size];
			Method getDef = def.getClass().getMethod(fn, int.class);
			// if no vals were provided use all defaults
			if (vals == null)
			{
				for(int i=0; i<size; i++)
				{
					array[i] = (float)getDef.invoke(def, i);
				}
			}
			else
			{
				for(int i=0; i<size; i++)
				{
					String v = vals[i];
					array[i] = (v != null && SimpleCSV.IsNumber(v))?
						Float.parseFloat(v):(float)getDef.invoke(def, i);
				}
			}
			return array;
		}
}
