package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.powerdata.openpa.AreaList;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.TwoTermBaseList;
import com.powerdata.openpa.impl.Nodump;

public class ListDumper
{
	static final char Dlm = ',';
	public void dump(PAModel model, File outdir) throws IOException,
			ReflectiveOperationException, RuntimeException
	{
		if (!outdir.exists()) outdir.mkdirs();
		Method[] methods = model.getClass().getMethods();
		for (Method m : methods)
		{
			if (m.getAnnotation(Nodump.class) == null)
			{
				Class<?> rtype = m.getReturnType();
				if (rtype.getPackage() != null
						&& rtype.getPackage().getName()
								.equals("com.powerdata.openpa"))
				{
//					while (rtype != null && rtype != Object.class
//							&& rtype != void.class)
					while(rtype.getInterfaces().length > 0)
					{
						Class<?> ifc = rtype.getInterfaces()[0];
						if ((ifc == BaseList.class||ifc == TwoTermBaseList.class)
								&& m.getParameterTypes().length == 0)
						{
							String nm = m.getName();
							String title = nm.substring(3);
							File nfile = new File(outdir, title + ".csv");
							System.err.format("Writing results of %s to %s\n", nm, nfile.getName());
							BaseList<?> list = (BaseList<?>) m.invoke(model,
								new Object[] {});
							if (list != null)
								dumpList(nfile, list);
							break;
						}
						rtype = ifc;
					}

				}
			}
		}
	}

	public void dumpList(File nfile, List<?> list) throws IOException,
			ReflectiveOperationException, IllegalArgumentException
	{
		Method[] methods = list.getClass().getMethods();
		ArrayList<Method> ometh = new ArrayList<>();
		ArrayList<String> mname = new ArrayList<>();
		for (Method m : methods)
		{
			if (m.getAnnotation(Nodump.class) == null)
			{
				Class<?> mclass = m.getReturnType();
				boolean isiterator = false;
				for (Class<?> i : mclass.getInterfaces())
				{
					if (i == Iterator.class)
					{
						isiterator = true;
						break;
					}
				}
				if (!checkInterface(mclass))
				{
				Class<?> mcsuper = mclass.getSuperclass();
				while (mcsuper != null && mcsuper != Object.class)
				{
					mclass = mcsuper;
					mcsuper = mclass.getSuperclass();
				}
				if (mclass != AbstractCollection.class && !mclass.isArray()
						&& mclass != void.class && mclass != Object.class
						&& !isiterator)
				{
					Class<?>[] ptype = m.getParameterTypes();
					if (ptype.length == 1 && ptype[0] == int.class)
					{
						String nm = m.getName();
						boolean yget = nm.startsWith("get");
						boolean yis = nm.startsWith("is");
						ometh.add(m);
						mname.add(nm.equals("get") ? "toString()" : nm
								.substring(yget ? 3 : (yis ? 2 : 0)));
					}
				}
				}
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
					Object v = null;
					try
					{
						v = ometh.get(j).invoke(list, i);	
					}
					catch(InvocationTargetException e)
					{
						v = "<null>";
					}
					boolean isstr = !Number.class.isInstance(v);
					if (isstr) pw.print('"');
					String vs = null;
					try
					{
						vs = v == null ? null : v.toString();
					}
					catch(NullPointerException e)
					{
						vs = "<null>";
					}
					pw.print((vs==null)?"<null>":vs);
					if (isstr) pw.print('"');
				}
				pw.println();
			}
			pw.close();
		}
	}
	
	private boolean checkInterface(Class<?> cl)
	{
		boolean rv = false;
		Class<?>[] ifc = cl.getInterfaces();
		for(Class<?> i : ifc)
		{
			if (i == BaseList.class)
			{
				rv = true;
			}
			else if (i != null)
				rv |= checkInterface(i);
		}
		return rv;
	}

	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File outdir = new File(System.getProperty("user.dir"));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					outdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.0001f);
		new ListDumper().dump(bldr.load(), outdir);
//		PAModel m = bldr.load();
//		BusList l = m.getSingleBus();
//		new ListDumper().dumpList(new File(outdir, "cmtest.csv"), l);
//		for(Bus b : l) System.err.format("%d %s\n", b.getIndex(), b.getName());
	}
}
