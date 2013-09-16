package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.IslandList;
import com.powerdata.openpa.psse.LogSev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.FactorizedBMatrix;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.SparseBMatrix;

public class FastDecoupledPowerFlow
{
	public static final float _Ptol = 0.005f;
	public static final float _Qtol = 0.005f;
	PsseModel _model;
	FactorizedBMatrix _bp, _bpp;
	SparseBMatrix _prepbpp;
	int[] _hotislands;
	
	public FastDecoupledPowerFlow(PsseModel model) throws PsseModelException
	{
		_model = model;
		setupHotIslands();
		buildMatrices();
		
	}

	public void runPowerFlow(VoltageSource vsrc) throws PsseModelException, IOException
	{
		runPowerFlow(null, vsrc);
	}
	
	public void runPowerFlow(MismatchReport mmr, VoltageSource vsrc) throws PsseModelException, IOException
	{
		int itermax = 40;
		BusList buses = _model.getBuses();
		int nbus = buses.size();

		PowerCalculator pcalc = (mmr == null) ? new PowerCalculator(_model)
				: new PowerCalculator(_model, mmr);
		float[] va=null, vm=null;

		switch(vsrc)
		{
			case Flat:
				va = new float[nbus];
				vm = flatMag();
				break;
				
			case Case:
				throw new UnsupportedOperationException("Not yet implemented");
				
			case RealTime:
				float[][] rtv = pcalc.getRTVoltages();
				va = rtv[0];
				vm = rtv[1];
		}

		for(Gen g : _model.getGenerators())
		{
			Bus b = g.getBus();
			BusTypeCode btc = b.getBusType();
			if (btc == BusTypeCode.Gen || btc == BusTypeCode.Slack)
			{
				//TODO:  resolve multiple setpoints if found
				int bndx = b.getIndex();
				if (vm[bndx] > 0f && vm[bndx] != g.getVS())
				{
					_model.log(LogSev.Error, b, "Generators have different voltage setpoints on same bus");
				}
				else
				{
					vm[bndx] = g.getVS();
				}
			}
		}

		int[] ldbus = _model.getBusNdxForType(BusTypeCode.Load);
		int[] genbus = _model.getBusNdxForType(BusTypeCode.Gen);
		int[][] pqbus = new int[][] {ldbus, genbus};
		int[][] pvbus = new int[][] {ldbus};
		int niter=0;

		for(int iiter=0; iiter < itermax; ++iiter)
		{
			float[][] mm = pcalc.calculateMismatches(va, vm);
			if (mmr != null)
			{
				mmr.report(String.valueOf(iiter));
			}
			boolean conv = testConverged(mm[0], pqbus, _Ptol);
			if (conv) conv &= testConverged(mm[1], pvbus, _Qtol);
			if (conv) {niter = iiter; break;}

			float[] dp = _bp.solve(mm[0], vm);
			for(int[] blist : pqbus)
			{
				for(int b : blist)
					va[b] += dp[b];
			}
			mm = pcalc.calculateMismatches(va, vm);
			if (mmr != null)
			{
				mmr.report(String.valueOf(iiter)+".5");
			}
			
			float[] dq = _bpp.solve(mm[1], vm);
			for(int b : ldbus)
				vm[b] += dq[b];

			
			
//			for(int b : ldbus)
//			{
//				va[b] += dp[b];
//				vm[b] += dq[b];
//			}
//			for(int b : genbus)
//				va[b] += dp[b];
		}
		System.out.format("Converged in %d iterations\n", niter);
	}
	

	float[] flatMag() throws PsseModelException
	{
		float[] vm = new float[_model.getBuses().size()];
		Arrays.fill(vm, 1f);
		return vm;
	}

	boolean testConverged(float[] mm, int[][] buses, float tol)
	{
		for(int[] blist : buses)
		{
			for (int b : blist)
				if (Math.abs(mm[b]) > tol) return false;
		}
		return true;
	}


	void setupHotIslands() throws PsseModelException
	{
		IslandList islands = _model.getIslands();
		int nhot = 0;
		int[] hotisl = new int[islands.size()];
		for(int i=0; i < islands.size(); ++i)
		{
			if (islands.get(i).isEnergized())
				hotisl[nhot++] = i; 
		}
		
		_hotislands = Arrays.copyOf(hotisl, nhot);
	}

	void buildMatrices() throws PsseModelException
	{
		LinkNet net = new LinkNet();
		ACBranchList branches = _model.getBranches();
		int nbus = _model.getBuses().size(), nbranch = branches.size();
		net.ensureCapacity(nbus-1, nbranch);
		float[] bselfbp = new float[nbus];
		float[] bbranchbp = new float[nbranch];
		float[] bselfbpp = new float[nbus];
		float[] bbranchbpp = new float[nbranch];
		
		for(Shunt shunt : _model.getShunts())
		{
			bselfbpp[shunt.getBus().getIndex()] -= shunt.getBpu();
		}
		
		for(ACBranch br : branches)
		{
			int fbus = br.getFromBus().getIndex();
			int tbus = br.getToBus().getIndex();
			int brx = net.findBranch(fbus, tbus);
			if (brx == -1)
			{
				brx = net.addBranch(fbus, tbus);
			}
			Complex z = br.getZ();
			float bbp = 1/z.im();
			Complex y = z.inv();
			
			bbranchbp[brx] -= bbp;
			bselfbp[fbus] += bbp;
			bselfbp[tbus] += bbp;
			float bbpp = -y.im();
			bbranchbpp[brx] -= bbpp;
			bselfbpp[fbus] += (bbpp - br.getFromBcm());
			bselfbpp[tbus] += (bbpp - br.getToBcm());
		}

		int[] pv = _model.getBusNdxForType(BusTypeCode.Gen);
		int[] slack = _model.getBusNdxForType(BusTypeCode.Slack);
		int[] bppbus = Arrays.copyOf(pv, pv.length+slack.length);
		System.arraycopy(slack, 0, bppbus, pv.length, slack.length);
		
		
		try
		{
			File tdir = new File(System.getProperty("java.io.tmpdir"));
			PrintWriter orgbp = openDebug(tdir, "bp-prep.csv");
			PrintWriter orgbpp = openDebug(tdir, "bpp-prep.csv");
			String hdr = "\"p\",\"pndx\",\"q\",\"qndx\",\"bbranch\",\"bself\"";
			orgbp.println(hdr);
			orgbpp.println(hdr);
			BusList buses = _model.getBuses();
			for (int i = 0; i < net.getBranchCount(); ++i)
			{
				int[] nodes = net.getBusesForBranch(i);
				int fbndx = nodes[0], tbndx = nodes[1];
				if (fbndx >= 0 && tbndx >= 0)
				{
					Bus fb = buses.get(fbndx), tb = buses.get(tbndx);
					orgbp.format("\"%s\",%d,\"%s\",%d,%f,%f\n", fb.getNAME(),
							fbndx, tb.getNAME(), tbndx, bbranchbp[i],
							bselfbp[fbndx]);
					orgbpp.format("\"%s\",%d,\"%s\",%d,%f,%f\n", fb.getNAME(),
							fbndx, tb.getNAME(), tbndx, bbranchbpp[i],
							bselfbpp[fbndx]);
				}
			}
			orgbp.close();
			orgbpp.close();
		} catch (IOException ioe)
		{
			throw new PsseModelException(ioe);
		}

		
		SparseBMatrix prepbp = new SparseBMatrix(net.clone(), slack, bbranchbp, bselfbp);
		_prepbpp = new SparseBMatrix(net, bppbus, bbranchbpp, bselfbpp);
		
		
		_bp = prepbp.factorize();
		_bpp = _prepbpp.factorize();
	}
	
	public static void main(String[] args) throws Exception
	{
		PsseModel model = PsseModel.OpenInput("pssecsv:raw=/home/chris/src/rod-tango/data/palco.raw&issolved=false");
//		PsseModel model = PsseModel.OpenInput("pssecsv:raw=/home/chris/src/rod-tango/data/op12s_pk_version_30.raw&issolved=false");
//		PsseModel model = PsseModel.OpenInput("pssecsv:raw=/home/chris/src/rod-tango/data/railbelt.raw&issolved=true");

		File tdir = new File("/tmp");
		File[] list = tdir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.startsWith("mismatch") && name.endsWith(".csv");
			}
		});
		for (File f : list) f.delete();
		
		File ddir = new File (System.getProperty("java.io.tmpdir"));
		MismatchReport mmr = new MismatchReport(model, ddir);
		
		FastDecoupledPowerFlow pf = new FastDecoupledPowerFlow(model);
		pf.dumpMatrices(ddir);
		pf.runPowerFlow(mmr, VoltageSource.Flat);
	}

	public void dumpMatrices(File tdir) throws IOException, PsseModelException
	{
		dumpMatrix(_bp, tdir, "factbp.csv");
		dumpMatrix(_bpp, tdir, "factbpp.csv");
	}
	
	void dumpMatrix(FactorizedBMatrix b, File tdir, String nm) throws IOException, PsseModelException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(tdir, nm))));
		b.dump(_model, pw);
		pw.close();
	}

	static PrintWriter openDebug(File tdir, String name) throws IOException
	{
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(tdir, name))));
	}
}
