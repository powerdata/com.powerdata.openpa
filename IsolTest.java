package com.powerdata.openpa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class IsolTest 
{

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

		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(outdir, "isoltest.txt"))));

		PAModel m = PflowModelBuilder.Create(uri).load();
		
		EquipLists<AbstractGroupObject> list = new EquipLists<>(m, new BusGrpMapBldr(m)
		{
			@Override
			protected boolean incSW(Switch d)
			{
				return !d.isOperableUnderLoad() && !d.isEnabled();
			}
		}.addAll().getMap());
		
		
		
		BusList buses = m.getBuses();
		Bus b0 = buses.get(buses.size()-1);
		pw.format("-- Get Group By Bus for Bus %s --\n", b0);
		PrintGrp(list.getByBus(b0), pw);
		
		pw.println("-- dump all isolation groups --");
		for(AbstractGroupObject g : list)
		{
			PrintGrp(g, pw);
		}
		
		pw.close();
	}
	
	static void PrintGrp(AbstractGroupObject g, PrintWriter pw)
	{
		pw.format("%s: ", g.toString());
		for(Bus b : g.getBuses())
			pw.format("%s, ", b.toString());
		pw.print("\nSwitches: ");
		for(Switch b : g.getSwitches())
			pw.format("%s, ", b.toString());
		pw.println();
		pw.print("\nLines: ");
		for(Line b : g.getLines())
			pw.format("%s, ", b.toString());
		pw.println();
	}
}
