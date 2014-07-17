package com.powerdata.openpa.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.SwitchList;

public class TestModel
{
	PAModel _m;
	public TestModel(PAModel m)
	{
		_m = m;
	}
	
	void dumpTopNodes(File file) throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		BusList buses = _m.getSingleBus();
		for(Bus b : buses)
		{
			out.format("%d %d %s\n", b.getIndex(), b.getKey(), b);
			out.format("\tBuses:\n");
			for(Bus b1 : b.getBuses())
				out.format("\t\t%d %d %s\n", b1.getIndex(), b1.getKey(), b1);
			out.format("\tSwitches:\n");
			for(Switch b1 : b.getSwitches())
				out.format("\t\t%d %d %s\n", b1.getIndex(), b1.getKey(), b1);
//			out.format("\tLines:\n");
//			for(Line b1 : b.getLines())
//				out.format("\t\t%s\n", b1);
			out.println();
		}
		out.close();
	}

	public void dumpBusLists(File fout) throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fout)));
		BusList buses = _m.getBuses();
		for(Bus b : buses)
		{
			out.format("%05d %05d %s\n", b.getIndex(), b.getKey(), b.toString());
			out.format("\tBuses:\n");
			for(Bus b1 : b.getBuses())
				out.format("\t\t%d %d %s\n", b1.getIndex(), b1.getKey(), b1.getName());

			out.format("\tSwitches:\n");
			for(Switch s : b.getSwitches())
				out.format("\t\t%d %d %s\n", s.getIndex(), s.getKey(), s.getName());
			
//			out.format("\tLines:\n");
//			for(Line s : b.getLines())
//				out.format("\t\t%s\n", s);
		}
		
		out.close();
	}

	public void dumpSwitches(File fout) throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fout)));
		SwitchList switches = _m.getSwitches();
		for(Switch b : switches)
		{
			out.format("%05d %05d %s\n", b.getIndex(), b.getKey(), b.toString());
		}
		out.close();
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
		
		PAModel m = PflowModelBuilder.Create(uri).load();
		TestModel tm = new TestModel(m);
		tm.dumpBusLists(new File(outdir, "buses.txt"));
		tm.dumpSwitches(new File(outdir, "switches.txt"));
		tm.dumpTopNodes(new File(outdir, "tnode.txt"));
		
	}

}
