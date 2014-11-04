package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.Set;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.CAWorker.Overload;
import com.powerdata.openpa.pwrflow.CAWorker.Results;
import com.powerdata.openpa.pwrflow.CAWorker.Status;
import com.powerdata.openpa.pwrflow.CAWorker.VoltViol;
import com.powerdata.openpa.pwrflow.ContingencySet.Contingency;

public class CATest extends BasicContingencyManager
{
	PrintWriter _pw;
	public CATest(PAModel m, IslandConv[] startPfResults, PrintWriter pw)
	{
		super(m, startPfResults);
		_pw = pw;
	}


	@Override
	protected void report(Contingency c, Results r, PAModel m) throws PAModelException
	{
		Set<Status> stat = r.getStatus();
		_pw.format("Contingency \"%s\": %s ", c.getName(), stat.toString());
		if (stat.contains(Status.LoadLoss))
			_pw.format("%7.3f%% system load loss", r.getLoadDropped()*100f);
		_pw.println();

		if (stat.contains(Status.HighVoltage)
				|| stat.contains(Status.VoltageCollapse))
		{
			for(VoltViol vv : r.getVoltViol())
			{
				_pw.format("\tvoltage @ %s: %f \n", vv.getBus().getName(), vv.getV());
			}
		}		
		if (stat.contains(Status.Overloads))
		{
			for (Entry<ListMetaType, Set<Overload>> oe : r.getOverloads().entrySet())
			{
				if (oe.getValue().size() > 0)
				{
					_pw.format("\t%s: ", oe.getKey().toString());
					for (Overload i : oe.getValue())
					{
						_pw.format("%s, ", _model.getList(oe.getKey()).getName(i.getIndex()));
					}
					_pw.println();
				}
			}
		}
	}

	@Override
	protected void recordException(Contingency c, PAModelException e)
	{
		System.err.format("Exception for contingency %s\n", c.toString());
		e.printStackTrace();
	}
	
	public static void main(String...args) throws Exception
	{
		String uri = null;
		File pout = new File(new File(System.getProperty("java.io.tmpdir")), "contingencyreport.txt");
		boolean ignoreRatings = false;
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
				case "output":
					pout = new File(args[i++]);
					break;
				case "noratings":
					ignoreRatings = true;
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --output output_file (deft to /tmp/contingencyreport.txt ]\n");
			System.exit(1);
		}
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.001f);
//		bldr.enableRCorrection(true);
//		bldr.setUnitRegOverride(true);
//		bldr.setBadXLimit(2f);
		PAModel m = bldr.load();
		
		IslandConv[] orig = null;

		{
			FDPFCore pf = new FDPFCore(m);
//			PFMismatchDbg dbg = new PFMismatchDbg(new File(new File("/run/shm/pfdbg"), "baseline"));
//			FDPFCore pf = dbg.getPF(m); 
			orig = pf.runPF();
			pf.updateResults();
//			dbg.write();
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pout)));
		CATest ca = new CATest(m, orig, pw);
//		ca.setParallel(true);
		ca.setDebug(true);
		if (ignoreRatings) ca.setIgnoreRatings(true);
		ContingencySet cset = new ContingencySet(m);
		long ts = System.currentTimeMillis();
		ca.runSet(cset);
		long te = System.currentTimeMillis() - ts;
		pw.format("%d contingencies in %d ms, avg=%fms\n", cset.size(), te,
			(double) te / ((double) cset.size()));
		pw.close();
	}
}
