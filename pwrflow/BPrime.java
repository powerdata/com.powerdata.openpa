package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;

public class BPrime<T extends ACBranchExt> extends BPrimeBase
{
	public BPrime(ACBranchAdjacencies<T> adj)
	{
		super(adj, new ACBranchBpElemBldr(adj));
	}
	
	public static void main(String...args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
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
					poutdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.0001f);
		bldr.setUnitRegOverride(false);
//		bldr.enableRCorrection(true);
		PAModel m = bldr.load();
		
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(m);
		// create extended list that computes Y and accounts for out-of-service branches
		ArrayList<ACBranchExtList<ACBranchExt>> acy = new ArrayList<>();
		for(ACBranchList list : m.getACBranches())
			acy.add(new ACBranchExtListI<ACBranchExt>(list, bri));
		ACBranchAdjacencies<ACBranchExt> adj = new ACBranchAdjacencies<>(acy, bri.getBuses().size());
		BPrime<ACBranchExt> bp = new BPrime<>(adj);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "bprime.csv"))));
		bp.dump(bri.getBuses().getName(), pw);
		pw.flush();
		pw.close();
	}
}
