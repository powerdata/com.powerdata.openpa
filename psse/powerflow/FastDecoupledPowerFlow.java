package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Island;
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

	public void runPowerFlow(VoltageSource vsrc) throws PsseModelException
	{
		runPowerFlow(null, vsrc);
	}
	
	public void runPowerFlow(MismatchReport mmr, VoltageSource vsrc) throws PsseModelException
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

			boolean conv = testConverged(mm[0], pqbus, _Ptol);
			if (conv) conv &= testConverged(mm[1], pvbus, _Qtol);
			if (conv) {niter = iiter; break;}

			float[] dp = _bp.solve(mm[0], pqbus);
			for(int[] blist : pqbus)
			{
				for(int b : blist)
					va[b] += dp[b];
			}
			mm = pcalc.calculateMismatches(va, vm);
			
			float[] dq = _bpp.solve(mm[1], pvbus);
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
		for(int b : _model.getBusNdxForType(BusTypeCode.Load))
			vm[b] = 1f;
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
		
		
		SparseBMatrix prepbp = new SparseBMatrix(net.clone(), slack, bbranchbp, bselfbp);
		_prepbpp = new SparseBMatrix(net, bppbus, bbranchbpp, bselfbpp);
		
		_bp = prepbp.factorize();
		_bpp = _prepbpp.factorize();
	}
	
	public static void main(String[] args) throws Exception
	{
//		PsseModel model = PsseModel.OpenInput("pssecsv:raw=/home/chris/src/psm/src/com/powerdata/openpa/psse/powerflow/2bustest.raw&issolved=false");
		PsseModel model = PsseModel.OpenInput("pssecsv:raw=/home/chris/src/rod-tango/data/4bustest.raw&issolved=false");
		PrintWriter mmout = new PrintWriter(new BufferedWriter(new FileWriter(new File("/tmp/mismatch.csv"))));
		MismatchReport mmr = new MismatchReport(model, mmout);
		
		FastDecoupledPowerFlow pf = new FastDecoupledPowerFlow(model);
		pf.runPowerFlow(mmr, VoltageSource.Flat);
		mmr.report();
		mmout.close();
	}
}
