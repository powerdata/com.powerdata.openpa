package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevList;

public class TestModel
{
	PAModel _m;
	public TestModel(PAModel m)
	{
		_m = m;
	}

	public void dumpBusLists(BusList buses, File fout) throws IOException, PAModelException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fout)));
		for(Bus b : buses)
		{
			out.format("%s\n", b.getID());
			out.format("\tBuses:\n");
			for(Bus b1 : b.getBuses())
				out.format("\t\t%s\n", b1.getID());

			out.format("\tTwo-Term Devs:\n");
			
			for(TwoTermDevList b1list : b.getTwoTermDevices())
			{
				for(TwoTermDev b1 : b1list)
					out.format("\t\t%s\n", b1.getID());
			}
			out.println();
			out.format("\tOne-Term Devs:\n");
			for (OneTermDevList b1list : b.getOneTermDevices())
			{
				for (OneTermDev b1 : b1list)
					out.format("\t\t%s\n", b1.getID());
			}
			out.println();
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
		tm.dumpBusLists(m.getBuses(), new File(outdir, "buses.txt"));
		tm.dumpBusLists(m.getSingleBus(), new File(outdir, "tnode.txt"));
		
	}

}
