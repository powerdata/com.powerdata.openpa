package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.IslandList;
import com.powerdata.openpa.psse.Load;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.Switch;
import com.powerdata.openpa.psse.SwitchState;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TransformerCtrlMode;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.psse.util.BusGroup;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;
import com.powerdata.openpa.psse.util.BusGroupList;
import com.powerdata.openpa.psse.util.ImpedanceFilter;
import com.powerdata.openpa.psse.util.MinZMagFilter;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.FactorizedFltMatrix;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PAMath;
//import com.powerdata.openpa.tools.SparseBMatrix;
/**
 * Utility to run a Fast-Decoupled AC Power Flow
 * 
 * @author chris@powerdata.com
 *
 */
public class FastDecoupledPowerFlow
{
	/** active power convergence tolerance */
	float _ptol = 0.005f;
	/** reactive power convergence tolerance */
	float _qtol = 0.005f;
	/** maximum iterations before giving up */
	int _itermax = 40;
	PsseModel _model;
	FactorizedFltMatrix _bp, _bpp;
//	SparseBMatrix _prepbpp;
	int[] _hotislands;
	float[] _vm, _va;
	boolean _adjusttaps = false;
	int[] _adjustablexfr = null;
	float _qtoltap = .05f;
	File _dbgdir;
	ImpedanceFilter _zfilt;
	BusGroup2TDevList _tn;
	
	public FastDecoupledPowerFlow(PsseModel model, ImpedanceFilter zfilt)
			throws PsseModelException, IOException
	{
		_model = model;
		System.err.println("construct power flow");
		if (model.getSwitches().isEmpty())
		{
			/*
			 * TODO: Make a simpler BusGroupList for unswitched models that can
			 * just pass through efficiently but maintain the interface
			 */
			
			_tn = new BusGroup2TDevList(model);
		}
		else
		{
			_tn = new BusGroup2TDevList(model)
			{
				@Override
				protected boolean incSW(Switch s) throws PsseModelException
				{
					return s.getState() == SwitchState.Closed;
				}
			}.addSwitches();
		}
		System.err.println("topological nodes complete");
		setupHotIslands();
		_zfilt = zfilt;
		System.err.println("build matrices");
		
		buildMatrices();
		System.err.println("done matrices");
	}

	public BusGroup2TDevList getTopologicalNodes() {return _tn;}
	
	public void setDebugDir(File dbgdir) throws IOException, PsseModelException
	{
		_dbgdir = dbgdir;
		if (_dbgdir.exists())
		{
			Stack<File> subdir = new Stack<>();
			subdir.push(dbgdir);
			for(File df : subdir)
			{
				File[] dirlist = df.listFiles();

				for (File d : dirlist)
				{
					if (d.isDirectory())
						subdir.push(d);
					else
						d.delete();
				}
				df.delete();
			}
		}
		_dbgdir.mkdirs();
		dumpMatrices(_dbgdir);
	}
	
	/** Set active power convergence tolerance (p.u. on 100MVA base) */
	public void setPtol(float ptol) {_ptol = ptol;}
	/** Get active power convergence tolerance (p.u. on 100MVA base) */
	public float getPtol() {return _ptol;}
	/** Set reactive power convergence tolerance (p.u. on 100MVA base) */
	public void setQtol(float qtol) {_qtol = qtol;}
	/** Get reactive power convergence tolerance (p.u. on 100MVA base) */
	public float getQtol() {return _qtol;}
	/** Get current impedance filter */
	public ImpedanceFilter getImpedanceFilter() {return _zfilt;}
	
	public PowerFlowConvergenceList runPowerFlow(VoltageSource vsrc)
			throws PsseModelException, IOException
	{
		boolean debug = _dbgdir != null;
		MismatchReport mmr = new MismatchReport(_model, _tn);
		PowerCalculator pcalc = new PowerCalculator(_model, _zfilt, _tn);
		if (debug)
		{
			pcalc.setDebugEnabled(mmr);
		}
		int nbus = _tn.size();
		
		if (_adjusttaps) 
			findAdjustableTransformers();

		float[] vm, va;

		switch(vsrc)
		{
			case RealTime:
				float[][] rtv = pcalc.getRTVoltages();
				va = rtv[0];
				vm = rtv[1];
				break;

			case LastSolved:
				va = _va;
				vm = _vm;
				break;
				
			case Flat:
			default:
				va = new float[nbus];
				vm = flatMag(BusTypeCode.Load);
				break;

		}

		for(Gen g : _model.getGenerators())
		{
			BusGroup b = _tn.findGroup(g.getBus());
			BusTypeCode btc = b.getBusType();
			if (btc == BusTypeCode.Gen || btc == BusTypeCode.Slack)
			{
				//TODO:  resolve multiple setpoints if found
				int bndx = b.getRootIndex();
//				vm[bndx] = g.getVS();
				vm[bndx] = b.getVMpu();
			}
		}

		boolean nconv=true;
		float[][] mm = pcalc.calculateMismatches(va, vm);
		if (debug) mmr.report(_dbgdir, "000");
		
		PowerFlowConvergenceList prlist = new PowerFlowConvergenceList(
				_hotislands.length);
		
		for(int iiter=0; iiter < _itermax && nconv; ++iiter)
		{
			/* 
			 * put this section in its own block to allow the results of solve to get cleaned up sooner 
			 */
			{
				float[] pmm = mm[0];
				int[] ebus=null;//TODO: = _bp.getEliminatedBuses();
				for(int bx : ebus) pmm[bx] /= vm[bx];
				float[] dp = _bp.solve(pmm);
				for(int bx : ebus) va[bx] += dp[bx];
			}
			
			mm = pcalc.calculateMismatches(va, vm);
			if (debug) mmr.report(_dbgdir, String.format("%02d-va", iiter));
			System.out.format("Iteration %d angle: ", iiter);
			nconv = notConverged(mm[0], mm[1], _ptol, _qtol, prlist, iiter);

			if (nconv)
			{
				float[] qmm = mm[1];
				int[] ebus=null;//TODO: = _bpp.getEliminatedBuses();
				for(int bx : ebus) qmm[bx] /= vm[bx];
				float[] dq = _bpp.solve(qmm);

				for(int bx : ebus) vm[bx] += dq[bx];
				mm = pcalc.calculateMismatches(va, vm);
				if (debug) mmr.report(_dbgdir, String.format("%02d-vm", iiter));
				System.out.format("Iteration %d voltage: ", iiter);
				nconv = notConverged(mm[0], mm[1], _ptol, _qtol, prlist, iiter);
			}
		}
		_vm = vm;
		_va = va;
		return prlist;
	}
	
	void findAdjustableTransformers() throws PsseModelException
	{
		TransformerList transformers = _model.getTransformers();
		int[] tndx = new int[transformers.size()];
		int nadj=0;
		for(Transformer t : transformers)
		{
			if (t.getRegStat() && t.getCtrlMode() == TransformerCtrlMode.Voltage)
			{
				tndx[nadj++] = t.getIndex();
			}
		}
		_adjustablexfr = Arrays.copyOf(tndx, nadj);
	}

	float[] flatMag(BusTypeCode type) throws PsseModelException
	{
		float[] vm = new float[_tn.size()];
		for(Bus b: _model.getIslands().getBusesForType(type))
			vm[_tn.findGrpNdx(b.getRootIndex())] = 1f;
		return vm;
	}

	boolean notConverged(float[] pmm, float[] qmm, float ptol, float qtol,
			PowerFlowConvergenceList res, int iter) throws PsseModelException
	{
		boolean nc = false;
		IslandList islands = _model.getIslands();
		for (int i=0; i < res.size(); ++i)
		{
			PowerFlowConvergence pr = res.get(i);
			boolean tnc = notConverged(pmm, qmm, islands.get(_hotislands[i]), ptol, qtol, pr); 
			nc |= tnc;
			if (!tnc) pr.setIterationCount(iter+1);
		}
		System.out.println();
		return nc;
	}

	boolean notConverged(float[] pmm, float[] qmm, Island island, float ptol,
			float qtol, PowerFlowConvergence r) throws PsseModelException
	{
		boolean pconv = true, qconv = true;
		BusGroup2TDevList pq = _tn.getForType(BusTypeCode.Load, island);
		BusGroup2TDevList pv = _tn.getForType(BusTypeCode.Gen, island);
		int pwndx = findWorst(pmm, new BusGroup2TDevList[] { pq, pv });
		int qwndx = findWorst(qmm, new BusGroup2TDevList[] {pq});
		if (pwndx == -1 && qwndx == -1) return false;
		System.out.format("\n\t%s", island.getObjectName());
		r.setWorstPbus(pwndx);
		if (pwndx != -1)
		{
			BusGroup pworst = _tn.get(pwndx);
			float mmw = pmm[pwndx];
			System.out.format("\n\t\tindex %d id %s name %s pmismatch %f", pwndx,
					pworst.getObjectID(), pworst.getDebugName(), mmw);
			r.setWorstPmm(mmw);
			pconv = Math.abs(mmw) < ptol;
		}
		
		r.setWorstQbus(qwndx);
		if (qwndx != -1)
		{
			BusGroup qworst = _tn.get(qwndx);
			float mmw = qmm[qwndx];
			System.out.format("\n\t\tindex %d id %s name %s qmismatch %f", qwndx,
					qworst.getObjectID(), qworst.getDebugName(), mmw);
			r.setWorstQmm(mmw);
			qconv = Math.abs(mmw) < ptol;
		}
		System.out.println();
		
		boolean conv = pconv && qconv;

		r.setConverged(conv);
		return !conv;
	}

	int findWorst(float[] mm, BusGroup2TDevList[] lists) throws PsseModelException
	{
		float wval = 0f;
		int wb = -1;
		for(int i=0; wb == -1 && i < lists.length; ++i)
		{
			for (BusGroup b : lists[i])
			{
				wb = b.getRootIndex();
				break;
			}
		}
		for (BusGroup2TDevList list : lists)
		{
			for (BusGroup bus : list)
			{
				int b = bus.getRootIndex();
				float am = Math.abs(mm[b]);
				if (am > wval)
				{
					wval = am;
					wb = b;
				}
			}
		}
		return wb;
	}

	void setupHotIslands() throws PsseModelException
	{
		IslandList islands = _model.getIslands();
		int nhot = 0;
		int[] hotisl = new int[islands.size()];
		for(int i=0; i < islands.size(); ++i)
		{
			if (islands.isEnergized(i))
				hotisl[nhot++] = i; 
		}
		
		_hotislands = Arrays.copyOf(hotisl, nhot);
	}
	
	public float[] getVA() {return _va;}
	public float[] getVM() {return _vm;}

	void buildMatrices() throws PsseModelException, IOException
	{
		LinkNet net = new LinkNet();
		ACBranchList branches = _model.getBranches();
		int nbus = _tn.size(), nbranch = branches.size();
		net.ensureCapacity(nbus-1, nbranch);
		float[] bselfbp = new float[nbus];
		float[] bbranchbp = new float[nbranch];
		float[] bselfbpp = new float[nbus];
		float[] bbranchbpp = new float[nbranch];
		
		for(Shunt s : _model.getShunts())
		{
			if (s.isInSvc())
			{
				bselfbpp[_tn.findGrpNdx(s.getBus())] -= s.getBpu();
			}
		}
		
		int nbr = branches.size();
		for(int i=0; i < nbr; ++i)
		{
			ACBranch br = branches.get(i);
			if (br.isInSvc())
			{
				int fbus = _tn.findGrpNdx(br.getFromBus());
				int tbus = _tn.findGrpNdx(br.getToBus());
				int brx = net.findBranch(fbus, tbus);
				if (brx == -1)
				{
					brx = net.addBranch(fbus, tbus);
				}
				Complex z = _zfilt.getZ(i);
				float bbp = 1 / z.im();

				bbranchbp[brx] -= bbp;
				bselfbp[fbus] += bbp;
				bselfbp[tbus] += bbp;
				float bbpp = -_zfilt.getY(i).im();
				bbranchbpp[brx] -= bbpp;
				bselfbpp[fbus] += (bbpp - br.getFromBchg() - br.getBmag());
				bselfbpp[tbus] += (bbpp - br.getToBchg() - br.getBmag());
			}
		}

//		IslandList islands = _model.getIslands();
		BusGroupList pv = _tn.getForType(BusTypeCode.Gen);
		BusGroupList slack = _tn.getForType(BusTypeCode.Slack);
		//TODO:  reimplement
//		int[] bppbus = prepBusArray(new BusGroup2TDevList[] {pv, slack});
//		
//		
//		SparseBMatrix prepbp = new SparseBMatrix(net, prepBusArray(new BusGroup2TDevList[]{slack}), bbranchbp, bselfbp);
//		_prepbpp = new SparseBMatrix(net, bppbus, bbranchbpp, bselfbpp);
//		
//		_bp = prepbp.factorize();
//		_bpp = _prepbpp.factorize();

	}
	
	int[] prepBusArray(BusGroup2TDevList[] lists) throws PsseModelException
	{
		int rvsz = 0, j=0;
		for(BusGroup2TDevList list : lists) rvsz += list.size();
		int[] rv = new int[rvsz];
		for(BusGroup2TDevList list : lists)
		{
			for (BusGroup b : list)
			{
				rv[j++] = b.getRootIndex();
			}
		}
		return rv;
	}
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		String svstart = "Flat";
		File dbgdir = null;
		File results = null;
		float minxmag = 0.001f;
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
				case "voltage":
					svstart = args[i++];
					break;
				case "debug":
					dbgdir = new File(args[i++]);
					break;
				case "results":
					results = new File(args[i++]);
					break;
				case "minxmag":
					minxmag = Float.parseFloat(args[i++]);
					break;
			}
		}
		
		VoltageSource vstart = VoltageSource.fromConfig(svstart);

		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri [-voltage flat|realtime] [ -minxmag smallest reactance magnitude (default to 0.001) ] [ -debug dir ] [ -results path_to_results]");
			System.exit(1);
		}
		
		PsseModel model = PsseModel.Open(uri);
		
		FastDecoupledPowerFlow pf = new FastDecoupledPowerFlow(model,
				new MinZMagFilter(model.getBranches(), minxmag));
		
		if (dbgdir != null) pf.setDebugDir(dbgdir);
		
		PowerFlowConvergenceList pslist = pf.runPowerFlow(vstart);
		
		System.out.println("Island Converged Iterations WorstPBus   Pmm   WorstQBus   Qmm   Load    Gen");
		IslandList islands = model.getIslands();
		BusGroup2TDevList topbus = pf.getTopologicalNodes();
		
		for(PowerFlowConvergence psol : pslist)
		{
			Island i = islands.get(psol.getIslandNdx());
			float ld = 0, gn = 0;
			for(Gen g : i.getGenerators())
				gn += g.getPS();
			for(Load l : i.getLoads())
				ld += l.getP();
			
			
			System.out.format("  %s     %5s       %2d     %9s %7.2f %9s %7.2f %7.2f %7.2f\n",
				i.getObjectName(),
				String.valueOf(psol.getConverged()),
				psol.getIterationCount(),
				(psol.getWorstPbus() == -1)?" -- ":topbus.get(psol.getWorstPbus()).getObjectName(),
				PAMath.pu2mva(psol.getWorstPmm(), model.getSBASE()),
				(psol.getWorstQbus() == -1)?" -- ":topbus.get(psol.getWorstQbus()).getObjectName(),
				PAMath.pu2mva(psol.getWorstQmm(), model.getSBASE()), ld, gn);
		}
		/*
		 * TODO:  results should probably be for the switched model, not the topological view
		 */
		if (results != null)
		{
			MismatchReport mmr = new MismatchReport(model, topbus);
			PowerCalculator pc = new PowerCalculator(model, topbus);
			pc.setDebugEnabled(mmr);
			pc.calculateMismatches(pf.getVA(), pf.getVM());
			mmr.report(results);
		}
		
	}

	public void dumpMatrices(File tdir) throws IOException, PsseModelException
	{
		dumpMatrix(_bp, tdir, "factbp.csv");
		dumpMatrix(_bpp, tdir, "factbpp.csv");
	}
	
	void dumpMatrix(FactorizedFltMatrix b, File tdir, String nm)
			throws IOException, PsseModelException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(tdir, nm))));
		//TODO:  reimplement
//		b.dump(_tn, pw);
		pw.close();
	}

	void adjustTransformerTaps(float[] vm, float[] va)
	{
		
	}
}
