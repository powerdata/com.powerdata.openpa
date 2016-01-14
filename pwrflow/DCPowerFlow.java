package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.Load;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.FactorizedFltMatrix;

public class DCPowerFlow
{
	PAModel _model;
	BusRefIndex _bri;
	BusTypeUtil _btu;
	BusList _buses;
	float _sbase = 100f;
	float[] _ang;
	Collection<ACBranchExtList<ACBranchExt>> _insvc;
	
	public DCPowerFlow(PAModel m, BusRefIndex bri, BusTypeUtil btu)
	{
		_model = m;
		_bri = bri;
		_btu = btu;
		_buses = _bri.getBuses();
	}
	public DCPowerFlow runPF() throws PAModelException
	{
		/*
		 * Build adjacency matrix using lists of the in-service branches
		 */
		_insvc = getInService();
		BPrime.MatrixElementBuilder bldr = new BPrime.MatrixElementBuilder(_buses.size(),
			_insvc.stream().mapToInt(i -> i.size()).sum());
		ACBranchAdjacencies adj = new ACBranchAdjacencies(_insvc, _buses, bldr);
		/*
		 * Generate and factorize the B' matrix
		 */
		FactorizedFltMatrix flm = new BPrime(adj, bldr).factorize(
			_btu.getBuses(BusType.Reference));
		/*
		 * Set up P mismatches for load & gen, and solve the angles
		 */
		_ang = flm.solve(setupMismatches());
		return this;
	}
	
	public void updateResults() throws PAModelException
	{
		for (List<ACBranchExt> list : _insvc)
		{
			for (ACBranchExt brx : list)
			{
				ACBranch br = brx.getBranch();
				float shift = _ang[brx.getFromBus().getIndex()]
						- _ang[brx.getToBus().getIndex()];
				float p = PAMath.pu2mva(shift / br.getX(), _sbase);
				br.setFromP(p);
				br.setToP(-p);
			}
		}
	}
	float[] setupMismatches() throws PAModelException
	{
		float[] mm = new float[_bri.getBuses().size()];
		setupGenMismatches(mm, SubLists.getGenInsvc(_model.getGenerators()));
		setupLoadMismatches(mm, SubLists.getLoadInsvc(_model.getLoads()));
		return mm;
	}

	
	
	void setupLoadMismatches(float[] mm, LoadList loadInsvc) throws PAModelException
	{
		for(Load l : loadInsvc)
		{
			mm[_buses.getByBus(l.getBus()).getIndex()] += 
				PAMath.mva2pu(l.getP(), _sbase);
		}
	}
	void setupGenMismatches(float[] mm, GenList genInsvc) throws PAModelException
	{
		for(Gen g : genInsvc)
		{
			mm[_buses.getByBus(g.getBus()).getIndex()] += 
				PAMath.mva2pu(g.getPS(), _sbase);
		}
	}

	private Collection<ACBranchExtList<ACBranchExt>> getInService() throws PAModelException
	{
		Collection<ACBranchExtList<ACBranchExt>> rv = new ArrayList<>();
		for (ACBranchList list : _model.getACBranches())
		{
			rv.add(new ACBranchExtSublist<ACBranchExt>(new ACBranchExtListI<>(
					list, _bri), SubLists.getInServiceIndexes(list)));
		}
		return rv;
	}
	public static void main(String... args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
		for (int i = 0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch (s.substring(ssx))
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
		bldr.setLeastX(0.0001f);
		PAModel m = bldr.load();
		for (ACBranchList list : m.getACBranches())
		{
			zero(list.getFromP(), list.getToP(), list.getFromQ(), list.getToQ());
		}
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(m);
		BusTypeUtil btu = new BusTypeUtil(m, bri);
		System.out.println("Slack Buses:");
		for (ElectricalIsland i : m.getElectricalIslands())
		{
			if (i.isEnergized())
			{
				System.out.format("\tIsland %s: %s\n", i.getName(), bri
						.getBuses().get(btu.getBuses(BusType.Reference)[0]));
			}
		}
		new DCPowerFlow(m, bri, btu).runPF().updateResults();
		new ListDumper().dump(m, outdir);
	}
	static void zero(float[]... arrays)
	{
		for (float[] a : arrays)
			Arrays.fill(a, 0f);
	}
}
