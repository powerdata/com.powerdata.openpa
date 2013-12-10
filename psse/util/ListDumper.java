package com.powerdata.openpa.psse.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.tools.BaseList;

public class ListDumper
{
	static final char Dlm = ',';
	static final Set<String> MethodFilter = 
		new HashSet<>(Arrays.asList(new String[] {"getPsseModel", "getClass", "isEmpty","getBusNdxsForType","getBusesForType"}));
		
	static final Set<String> ListFilter = 
		new HashSet<>(Arrays.asList(new String[] {"getBus", "getClass"}));
	
	public void dump(PsseModel model, File outdir) throws IOException,
			ReflectiveOperationException, RuntimeException
	{
		if (!outdir.exists()) outdir.mkdirs();
		Method[] methods = model.getClass().getMethods();
		for (Method m : methods)
		{
			String nm = m.getName();
			if (nm.startsWith("get") && nm.endsWith("s") && !ListFilter.contains(nm))
			{
				String title = nm.substring(3);
				File nfile = new File(outdir, title+".csv");
				BaseList<?> list = (BaseList<?>) m.invoke(model, new Object[] {});
				dumpList(nfile, list);
			}
		}
	}

	void dumpList(File nfile, BaseList<?> list) throws IOException,
			ReflectiveOperationException, IllegalArgumentException
	{
		Method[] methods = list.getClass().getMethods();
		ArrayList<Method> ometh = new ArrayList<>();
		ArrayList<String> mname = new ArrayList<>();
		for (Method m : methods)
		{
			String nm = m.getName();
			boolean yget = nm.startsWith("get");
			boolean yis = nm.startsWith("is");
			if ((yget || yis) && nm.length() > 3
					&& !MethodFilter.contains(nm))
			{
				ometh.add(m);
				mname.add(nm.substring(yget?3:2));
			}
		}
		int n = list.size();
		if (!ometh.isEmpty() && n > 0)
		{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					nfile)));
			pw.print(mname.get(0));
			for (int i = 1; i < mname.size(); ++i)
			{
				pw.print(Dlm);
				pw.print(mname.get(i));
			}
			pw.println();

			/* output data for each row */
			for (int i = 0; i < n; ++i)
			{
				for (int j = 0; j < ometh.size(); ++j)
				{
					/* output cell */
					if (j>0) pw.print(Dlm);
					Object v = ometh.get(j).invoke(list, i);
					boolean isstr = !Number.class.isInstance(v);
					if (isstr) pw.print('\'');
					String vs = v == null ? null : v.toString();
					pw.print((vs==null)?"<null>":vs);
					if (isstr) pw.print('\'');
				}
				pw.println();
			}
			pw.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		PsseModel model = PsseModel.Open("pssecsv:path=/tmp/pjm");
		File outdir = new File("/tmp/pjmmodel");
		new ListDumper().dump(model, outdir);
	}

}
