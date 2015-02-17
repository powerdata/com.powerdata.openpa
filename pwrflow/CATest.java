package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.CAWorker.Result;
import com.powerdata.openpa.pwrflow.CAWorker.Status;
import com.powerdata.openpa.pwrflow.ContingencySet.Contingency;

public class CATest extends BasicContingencyManager
{
	PrintWriter _pw;
	public CATest(PAModel m, ConvergenceList startPfResults, PrintWriter pw)
	{
		super(m, startPfResults);
		_pw = pw;
		_pw.println("Contingency,ContDevType,ContFrArea,ContToArea,Violation,ViolDevType,Type,Value");
	}

	static Set<Status> _VoltageViol = EnumSet.of(Status.HighVoltageFail, Status.LowVoltage, Status.VoltageCollapse, Status.HighVoltage);
	
	@Override
	protected void report(Contingency c, Set<Result> r, PAModel m) throws PAModelException
	{
		
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
		
		ConvergenceList orig = null;

		{
			FDPowerFlow pf = new FDPowerFlow(m, BusRefIndex.CreateFromSingleBuses(m));
			orig = pf.runPF();
			pf.updateResults();
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pout)));
		CATest ca = new CATest(m, orig, pw);
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
