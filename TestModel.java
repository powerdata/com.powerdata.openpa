package com.powerdata.openpa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
		SingleBusList buses = new SingleBusList(_m);
		for(Bus b : buses)
		{
			out.format("%d %s\n", b.getKey(), b);
			out.format("\tBuses:\n");
			for(Bus b1 : b.getBuses())
				out.format("\t\t%d %s\n", b1.getKey(), b1);
			out.format("\tSwitches:\n");
			for(Switch b1 : b.getSwitches())
				out.format("\t\t%d %s\n", b1.getKey(), b1);
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
				out.format("\t\t%s\n", b1);

			out.format("\tSwitches:\n");
			for(Switch s : b.getSwitches())
				out.format("\t\t%s\n", s);
			
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
		PAModel m = PflowModelBuilder.Create(
			"pd2cim:sdb=/tmp/config.pddb&db=/home/chris/pdc.workarea/ephem/1/exports/cim.pddb")
			.load();
		TestModel tm = new TestModel(m);
		tm.dumpBusLists(new File("/tmp/buses.txt"));
//		tm.dumpSwitches(new File("/tmp/switches.txt"));
		tm.dumpTopNodes(new File("/tmp/tnode.txt"));
		
	}

}
