package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.Load;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.pwrflow.ACBranchFlows.ACBranchFlow;
import com.powerdata.openpa.tools.FactorizedFltMatrix;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.SpSymMtrxFactPattern;

/**
 * Fast-decoupled AC Power Flow
 * 
 * 
 * 
 * @author chris@powerdata.com
 *
 */

public class FDPowerFlow
{
	public interface PFMismatchReporter
	{
		void reportBegin(BusList buses);
		void reportMismatch(Mismatch pmm, Mismatch qmm, float[] vm, float[] va, BusType[] type);
		void reportEnd();
	}
	/** Bus types used to compare active power mismatches */
	static Collection<BusType> BT_ACTIVE = EnumSet.complementOf(EnumSet.of(BusType.Reference));
	/** Bus types used to compare reactive power mismatches */
	static Collection<BusType> BT_REACTIVE = EnumSet.of(BusType.PQ);
	
	PAModel _model;
	/** Track the single-bus topology view */
	BusRefIndex _bri;
	/** ac branch calculators for each branch */
	Collection<ACBranchFlows> _brcalc = new ArrayList<>();
	/** Network adjacency matrix */
	ACBranchAdjacencies<ACBranchFlow> _adj;
	/** Bus types */
	BusTypeUtil _btu;
	/** matrix elimination pattern for B'' bus type changes */
	SpSymMtrxFactPattern _pat;
	/** Factorized B' matrix */
	FactorizedFltMatrix _bPrime;
	/** B'' matrix */
	BDblPrime<ACBranchFlow> _bdblprime_mtrx;
	/** factorized B' matrix */
	volatile FactorizedFltMatrix _bDblPrime = null;
	/** Maximum number of iterations */
	int _maxit = 40;
	/** Convergence Tolerance */
	float _tol = 0.005f;
	/** system MVA base */
	float _sbase = 100f;
	/** PV Buses */
	BusList _pvbuses;
	/** system Buses (single-bus view) */
	BusList _buses;
	/** Track the voltage set points for PV buses.  Note that remote regulated buses
	 * are not PV buses, but instead are managed to adjust the PV bus setpoints  */
	VoltageSetPoint _vsp;
	/** energized islands */
	IslandList _hotislands;
	/** Active generators */
	GenList _actvgen;
	/** active generators controlling voltage (ordered as _actvgen) */
	boolean[] _isavr;
	/** active loads */
	LoadList _actvload;
	/** SVC calculator */
	SVCCalcList _svc;
	/** fixed shunt calculators */
	ArrayList<FixedShuntCalcList> _fshcalc = new ArrayList<>();
	/** monitor vars */
	//TODO:  GenVarMonitor _varmon;
	/** report mismatches externally */
	List<PFMismatchReporter> _mmreport = new ArrayList<>();
	/** resulting voltage magnitudes (p.u.) */
	float[] _vm;
	/** resulting voltage angles (rad) */
	float[] _va;
	
	public FDPowerFlow(PAModel model, BusRefIndex bri) throws PAModelException
	{
		_model = model;
		
		/* Create single-bus view of topology */
		_bri = bri;
		_buses = _bri.getBuses();
		
		/* build AC branch calculators (one for each dev type) */
		for(ACBranchList l : model.getACBranches())
			_brcalc.add(new ACBranchFlowsSubList(new ACBranchFlowsI(l, _bri),
				SubLists.getInServiceIndexes(l)));
		
		/* build adjacency matrix */
		_adj = new ACBranchAdjacencies<>(_brcalc, 
				_buses.size());

		List<FixedShuntListIfc<? extends FixedShunt>> shInSvc = new ArrayList<>(2);
		shInSvc.add(SubLists.getShuntCapInsvc(_model.getShuntCapacitors()));
		shInSvc.add(SubLists.getShuntReacInsvc(_model.getShuntReactors()));
		
		/* partition SVC's into 0 and non-0 slope (0-slope will become PV buses) */
		SVCList[] psvc = partitionSVCs();
		SVCList nonPvSvc = psvc[1];
		_svc = new SVCCalcList(nonPvSvc, _bri);
		

		/* organize the model into bus types and select reference buses for each island */
		//TODO:  Externalize BusTypeUtil creation so that it can be created once for multiple uses
		_btu = new BusTypeUtil(model, _bri, psvc[0]);		
		/* build factorization pattern */
		_pat = new SpSymMtrxFactPattern();
		_pat.eliminate(_adj, _btu.getBuses(BusType.Reference));
		
		/* Build B' (store it already factorized) */
		_bPrime = new BPrime<>(_adj).factorize(_pat);
		
		/* Build B'' (keep the actual matrix object to allow for changes of element values) */
		_bdblprime_mtrx = new BDblPrime<ACBranchFlow>(_adj, shInSvc, _svc, _bri);
		
		/* Build a list of buses with type PV */
		_pvbuses = SubLists.getBusSublist(_buses, 
			_btu.getBuses(BusType.PV));
		
		for(Bus b : _pvbuses)
			_bdblprime_mtrx.incBdiag(b.getIndex(), 1e+06f);
		
		setupHotIslands();
		
		 _vsp = new VoltageSetPoint(_pvbuses, _buses, _model.getIslands().size());
		
		/** set up lists for remaining equipment */
		_actvload = SubLists.getLoadInsvc(_model.getLoads());

		/* set up the fixed shunt calculators */
		for(FixedShuntListIfc<? extends FixedShunt> fsh : shInSvc)
			_fshcalc.add(new FixedShuntCalcList(fsh, _bri));
	}
	
	/**
	 * Partition the SVC's into elements that should be treated as PV buses (0-slope)
	 * @return Index 0 has SVC's treated as PV buses, 1 has the rest
	 * @throws PAModelException
	 */
	SVCList[] partitionSVCs() throws PAModelException
	{
		SVCList svcs = _model.getSVCs();
		int[] insvc = SubLists.getInServiceIndexes(svcs);
		int nsvc = insvc.length;
		int[] pv = new int[nsvc], npv = new int[nsvc];
		int pvc=0, npvc=0;
		for(int i : insvc)
		{
			if(svcs.getSlope(i) == 0f)
				pv[pvc++] = i;
			else
				npv[npvc++] = i;
		}
		return new SVCList[] {SubLists.getSVCSublist(_model.getSVCs(), Arrays.copyOf(pv, pvc)), 
				SubLists.getSVCSublist(_model.getSVCs(), Arrays.copyOf(npv, npvc))};
	}

	void setupHotIslands() throws PAModelException
	{
		IslandList islands = _model.getIslands();
		int nisl = islands.size(), nhot = 0;
		int[] idx = new int[nisl];
		
		GenList gens = _model.getGenerators();
		int ngen = gens.size(), np=0;
		int[] pidx = new int[ngen];
		boolean[] qidx = new boolean[ngen];
		
		for(Island island : islands)
		{
			boolean h = false;
			for(Gen g : island.getGenerators())
			{
				if (g.isGenerating())
				{
					if (!h)
					{
						h = true;
						idx[nhot++] = island.getIndex();
					}
					pidx[np] = g.getIndex();
					qidx[np++] = g.isRegKV();
				}
			}
		}
		_hotislands = SubLists.getIslandSublist(islands, Arrays.copyOf(idx, nhot));
		_actvgen = SubLists.getGenSublist(_model.getGenerators(), Arrays.copyOf(pidx, np));
		_isavr = Arrays.copyOf(qidx, np);
	}

	FactorizedFltMatrix getBDblPrime()
	{
		if (_bDblPrime == null)
		{
			_bDblPrime = _bdblprime_mtrx.factorize(_pat);
		}
		return _bDblPrime;
	}
	
	static Collection<BusType> _ActvMismatchTypes = EnumSet.of(BusType.PQ, BusType.PV);
	static Collection<BusType> _ReacMismatchTypes = EnumSet.of(BusType.PQ);
	
	/** 
	 * Run the power flow
	 * @return Power Flow convergence results 
	 * @throws PAModelException 
	 */
	public ConvergenceList runPF() throws PAModelException
	{
		/** voltage mag working array */
		_vm = PAMath.vmpu(_buses);
		/** voltage angle working array */
		_va = PAMath.deg2rad(_buses.getVA());
		/** active power mismatches */
		Mismatch pmm = new Mismatch(_bri, _btu, _ActvMismatchTypes);
		/** reactive power mismatches */
		Mismatch qmm = new Mismatch(_bri, _btu, _ReacMismatchTypes);
		/** Convergence information for each island */
		ConvergenceList rv = new ConvergenceList(_hotislands, _btu, pmm, qmm, _tol, _tol, _vm);
		/** apply voltage setpoints to vm */
		_vsp.applyToVMag(_vm);
		
		for(PFMismatchReporter r : _mmreport)
			r.reportBegin(_buses);

		
		boolean incomplete = true;
		for(int it=0; incomplete && it < _maxit; ++it)
		{
			/* apply mismatches to both P and Q */
			applyMismatches(pmm, qmm, _vm, _va);
			
			/* test for convergence */
			incomplete = !rv.test();
			
			/* solve a new set of voltages and angles */
			if (incomplete)
			{
				/* check remote-monitored buses and adjust any setpoints as needed */
				_vsp.applyRemotes(_vm, rv);
				/* correct magnitudes */
				applyCorrections(_vm, _vm, getBDblPrime(), qmm);
				/* correct angles */
				applyCorrections(_va, _vm, _bPrime, pmm);
			}
			
		}
		
		for(PFMismatchReporter r : _mmreport)
			r.reportEnd();

		return rv;
	}

	void applyCorrections(float[] state, float[] vm, FactorizedFltMatrix b, Mismatch mm)
	{
		float[] m = mm.get();
		for(int bus : b.getElimBus())
			m[bus] /= vm[bus];
		float[] c = b.solve(m);
		for(int bus : b.getElimBus())
			state[bus] -= c[bus];
	}

	/**
	 * Solve branch equations, apply results and bus injections to mismatch arrays
	 * @param pmm Active power mismatches
	 * @param qmm Reactive power mismatches
	 * @param vm Solved voltage magnitudes (parallel with _buses)
	 * @param va Solved voltage angles (parallel with _buses)
	 * @throws PAModelException 
	 */
	void applyMismatches(Mismatch pmm, Mismatch qmm, float[] vm, float[] va) throws PAModelException
	{
		pmm.reset();
		qmm.reset();
		
		for(ACBranchFlows flows : _brcalc)
		{
			flows.calc(vm, va);
			flows.applyMismatches(pmm, qmm);
		}
		
		for(FixedShuntCalcList fsh : _fshcalc)
		{
			fsh.calc(vm);
			fsh.applyMismatches(qmm);
		}
		
		_svc.calc(vm);
		_svc.applyMismatches(qmm);
		
		applyGenMism(pmm, qmm);
		applyLoadMism(pmm, qmm);
		
		for(PFMismatchReporter r : _mmreport)
			r.reportMismatch(pmm, qmm, vm, va, _btu.getTypes());
	}


	void applyLoadMism(Mismatch pmm, Mismatch qmm) throws PAModelException
	{
		float[] p = pmm.get(), q = qmm.get();
		for(Load l : _actvload)
		{
			int b = _buses.getByBus(l.getBus()).getIndex();
			p[b] -= PAMath.mva2pu(l.getP(), _sbase);
			q[b] -= PAMath.mva2pu(l.getQ(), _sbase);
		}
	}

	void applyGenMism(Mismatch pmm, Mismatch qmm) throws PAModelException
	{
		float[] p = pmm.get(), q = qmm.get();
		int ngen = _actvgen.size();
		int[] bx = _bri.get1TBus(_actvgen);
		for(int i=0; i < ngen; ++i)
		{
			Gen g = _actvgen.get(i);
			int b = bx[i];
			p[b] -= PAMath.mva2pu(g.getPS(), _sbase);
			if(!_isavr[i]) q[b] -= PAMath.mva2pu(g.getQS(), _sbase);
		}
	}
	
	public void addMismatchReporter(PFMismatchReporter r)
	{
		_mmreport.add(r);
	}
	
	/** update bus results to local model 
	 * @throws PAModelException */
	public void updateBusResults() throws PAModelException
	{
		int nbus = _buses.size();
		for(int i=0; i < nbus; ++i)
		{
			_buses.setVM(i, _vm[i] * _buses.getVoltageLevel(i).getBaseKV());
			_buses.setVA(i, PAMath.rad2deg(_va[i]));
		}
	}

	public void updateResults() throws PAModelException
	{
		updateBusResults();
		for(FixedShuntCalcList l : _fshcalc)
			l.update();
		_svc.update();
		for(ACBranchFlows l : _brcalc)
			l.update();
	}
	
	public void setMaxIterations(int i)
	{
		_maxit = i;
	}

	
	@Deprecated
	public BusTypeUtil getBusTypes() {return _btu;}
	
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
//		bldr.enableRCorrection(true);
		PAModel m = bldr.load();

		FDPowerFlow pf = new FDPowerFlow(m, BusRefIndex.CreateFromSingleBus(m));
		pf.addMismatchReporter(new PFMismatchDbg(outdir));
		ConvergenceList results = pf.runPF();
		results.forEach(l -> System.out.println(l));
	}


}
