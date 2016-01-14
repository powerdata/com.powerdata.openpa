package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.matrix.SpSymFltMatrix;

public class BPrime extends SpSymFltMatrix 
{
	public static class MatrixElementBuilder extends
			ACBranchAdjacencies.MatrixElementBuilder
	{
		
		public MatrixElementBuilder(int nbus, int nbranch)
		{
			super(nbus, nbranch);
		}
		
		@Override
		public void build(ACBranchExt branch, int branchIndex, int frombus, int tobus)
			throws PAModelException
		{
			float y = 1f / branch.getBranch().getX();
			_bdiag[frombus] += y;
			_bdiag[tobus] += y;
			_boffdiag[branchIndex] -= y;
		}
	}
	
	float[] _bdiag, _boffdiag;
	
	/**
	 * Constructor for sharing of adjacencies & external element building
	 * 
	 * @param adj branch adjacency matrix
	 * @param bldr class to build elements for the matrix
	 */
	public BPrime(ACBranchAdjacencies adj, MatrixElementBuilder bldr)
	{
		construct(adj, bldr);
	}
	
	protected void construct(ACBranchAdjacencies adj, MatrixElementBuilder bldr)
	{
		setAdjacencies(adj);
		setBDiag(bldr.getBDiag());
		setBOffDiag(bldr.getBOffDiag());
	}
	
	/**
	 * Construct B' directly from the model
	 * 
	 * @param model
	 * @throws PAModelException
	 */
	public BPrime(PAModel model) throws PAModelException
	{
		/* Organize buses into a single-bus (study) topology */
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(model);
		BusList buses = bri.getBuses();
		
		/* restrict branches to those in-service */
		List<ACBranchList> branches = SubLists.getBranchInsvc(model.getACBranches());
		
		/* create an element builder suitable for b' */
		MatrixElementBuilder bldr = new MatrixElementBuilder(buses.size(), 
			branches.stream().mapToInt(i -> i.size()).sum());
		
		/*
		 * Create adjacenies for all the AC branches. This also has the ability
		 * to create the matrix elements at the same time for convenience
		 */
		ACBranchAdjacencies adj = new ACBranchAdjacencies(
			ACBranchExtList.LoadExtension(branches, bri),buses, bldr);
		
		/* construct the actual matrix with both adjacencies and values */
		construct(adj, bldr);
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
		bldr.setLeastX(0.0001f);
		PAModel m = bldr.load();
		BPrime bp = new BPrime(m);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "bp.csv"))));
		bp.dump(m.getSingleBus().getName(), pw);
		pw.flush();
		pw.close();
		
	}
}
