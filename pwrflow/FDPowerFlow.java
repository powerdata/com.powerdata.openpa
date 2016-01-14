package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.pwrflow.BusMonitor.Action;
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.FactorizedFltMatrix;
import com.powerdata.openpa.tools.matrix.SpSymMtrxFactPattern;
import com.powerdata.openpa.tools.psmfmt.PsmFmtExport;

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
	/** Bus types used to compare active power mismatches */
	static Collection<BusType> BT_ACTIVE = EnumSet.complementOf(EnumSet.of(BusType.Reference));
	/** Bus types used to compare reactive power mismatches */
	static Collection<BusType> BT_REACTIVE = EnumSet.of(BusType.PQ);
	
	PAModel _model;
	/** Track the single-bus topology view */
	BusRefIndex _bri;
	/** Network adjacency matrix */
	ACBranchAdjacencies _adj;
	/** Bus types */
	BusTypeUtil _btu = null;
	/** matrix elimination pattern for B'' bus type changes */
	SpSymMtrxFactPattern _pat;
	/** Factorized B' matrix */
	FactorizedFltMatrix _bPrime;
	/** B'' matrix */
	BDblPrime _bdblprime_mtrx;
	/** factorized B' matrix */
	volatile FactorizedFltMatrix _bDblPrime = null;
	/** Maximum number of iterations */
	int _maxit = 40;
	/** Convergence Tolerance */
	float _cnvtol = 0.005f;
	/** Unit limit tolerance when distributing slack */
	float _pdisttol = 1f;
	/** slack dist activation tolerance */
	float _pdistacttol = 0.1f;
	/** system MVA base */
	float _sbase = 100f;
	/** system Buses (single-bus view) */
	BusList _buses;
	/** Track the voltage set points for PV buses.  Note that remote regulated buses
	 * are not PV buses, but instead are managed to adjust the PV bus setpoints  */
	VoltageSetPoint _vsp;
	/** energized islands */
	ElectricalIslandList _hotislands;
	/** monitor vars */
	GenVarMonitor _varmon;
	/** Monitor slack & distribute */
	DistributedSlackMonitor _dsmon = null;
	/** report mismatches externally */
	List<MismatchReporter> _mmreport = new ArrayList<>();
	/** resulting voltage magnitudes (p.u.) */
	float[] _vm;
	/** resulting voltage angles (rad) */
	float[] _va;
	/** AC power calculator to calculate flows and update mismatches */
	private ACPowerCalc _accalc;
	/** active generators (keep around so we can change AVR characteristics on bus type changes)*/
	ActiveGenData _actvgen;
	/** Keep the reactive mismatches around in order to update generators and SVC's */
	Mismatch _qmm;
	/** track single-buses in order of hot island */
	
	/**
	 * Track slack participation for each (hot) island.
	 * @author chris@powerdata.com
	 *
	 */
	class DistInfo
	{
		GenList _gens;
		float _max;
		DistInfo(GenList gens) throws PAModelException
		{
			_gens = gens;
			_max = 0f;
			for(Gen g : gens) _max += g.getOpMaxP();
		}
		GenList getGens() {return _gens;}
		/**
		 * Distribute slack
		 * @param mm mismatch in MW
		 * @throws PAModelException
		 */
		
		
		
		abstract class Distributor
		{
			@Override
			public String toString()
			{
				return String.format("mm=%f, rem=%f, dmax=%f, _max=%f, _reduced=%f\n",
					_mm, _rem, _dmax, _max, _reduced);
			}
			float _rem, _dmax = _max, _reduced = 0f;
			boolean[] _avail = new boolean[_gens.size()];
			float _mm;
			Distributor(float mm)
			{
				_rem = -mm;
				_mm = -mm;
				Arrays.fill(_avail, true);
			}
			
			void distribute() throws PAModelException
			{
				int n = _gens.size();
				while(testRemaining())
				{
					_dmax -= _reduced;
					for(int i=0; i < n; ++i)
					{
						Gen g = _gens.get(i);
						if (_avail[i])
						{
							float pmax = g.getOpMaxP();
							float pemer = pmax * _pdisttol;
							float prof = g.getOpMaxP() / _dmax;
							float pdes = prof * _mm, ps = g.getPS();
							float pavail = calcAvail(pdes, pemer, ps);
							if(testAvail(pdes, pavail))
							{
								_avail[i] = false;
								pdes = pavail;
								_reduced += pmax;
							}
							g.setPS(ps+pdes);
							_rem -= pdes;
						}
					}
				}
			}
			abstract boolean testRemaining();
			abstract boolean testAvail(float pdes, float pavail);
			abstract float calcAvail(float pdes, float pemer, float ps);
		}
		
		class PosMMDistributor extends Distributor
		{
			PosMMDistributor(float mm) {super(mm);}
			@Override
			boolean testRemaining() {return (_rem > _cnvtol);}
			@Override
			boolean testAvail(float pdes, float pavail) {return (pavail > pdes);}
			@Override
			float calcAvail(float pdes, float pemer, float ps) {return Math.min(pdes,  pemer - ps);}
		}
		
		class NegMMDistributor extends Distributor
		{
			NegMMDistributor(float mm) {super(mm);}
			@Override
			boolean testRemaining() {return (_rem < -_cnvtol);}
			@Override
			boolean testAvail(float pdes, float pavail) {return (pdes > pavail);}
			@Override
			float calcAvail(float pdes, float pemer, float ps) {return Math.max(pdes,  -ps);}
		}
		
		void distribute(float mm) throws PAModelException 
		{
			((mm > 0f) ?new NegMMDistributor(mm) : new PosMMDistributor(mm)).distribute(); 
		}
	}

	/**
	 * Monitor slack buses and take action to distribute
	 * @author chris@powerdata.com
	 *
	 */
	class DistributedSlackMonitor extends BusMonitor
	{
		List<DistInfo> _dilist;
		Monitor _slkmon = (mm, i) -> 
		{
			_dilist.get(i).distribute(PAMath.pu2mva(mm, _sbase));
			return false;
		};


		DistributedSlackMonitor() throws PAModelException
		{
			super(_buses, FDPowerFlow.this._btu, EnumSet.of(BusType.Reference), _hotislands);
			_dilist = new ArrayList<>(_hotislands.size());
			for(ElectricalIsland h : _hotislands)
				_dilist.add(new DistInfo(findOnlineGens(h.getGenerators())));
			Arrays.fill(_monitors, _slkmon);

		}
		/** restrict down to generators that are actually generating MW */
		GenList findOnlineGens(GenList generators) throws PAModelException
		{
			int nrv = 0, ng = generators.size();
			int[] rv = new int[ng];
			for (int i=0; i < ng; ++i)
			{
				Gen g = generators.get(i);
				if (g.isGenerating())
					rv[nrv++] = i;
			}
			return SubLists.getGenSublist(generators, Arrays.copyOf(rv, nrv));
		}
		@Override
		protected boolean testIsland(ConvergenceInfo ci)
		{
			return Math.abs(ci.getWorstP().getValue()) < _pdistacttol;
		}

	}
	
	public FDPowerFlow(PAModel model, BusRefIndex bri) throws PAModelException
	{
		_model = model;

		cleanupOldResults();
		
		/* Create single-bus view of topology */
		_bri = bri;
		_buses = _bri.getBuses();
		
		setupHotIslands();
		
		Collection<FixedShuntListIfc<? extends FixedShunt>> fsh = ACPowerCalc.setupFixedShunts(model);
		_accalc = new ACPowerCalc(model, bri, fsh, ACPowerCalc.setupActiveLoads(bri, model, _sbase), _actvgen);
		
		int nbranch = _accalc.getBranchFlows().stream().mapToInt(i -> i.size()).sum(); 
		BPrime.MatrixElementBuilder bldrBp = new BPrime.MatrixElementBuilder(_buses.size(), nbranch);
		BDblPrime.MatrixElementBuilder bldrBpp = new BDblPrime.MatrixElementBuilder(_buses.size(), nbranch);
		bldrBpp.addFixedShunts(fsh, bri, _sbase);

		/* build adjacency matrix */
		_adj = new ACBranchAdjacencies(_accalc.getBranchFlows(), _buses, bldrBp, bldrBpp);

		/* organize the model into bus types and select reference buses for each island */
		_btu = new BusTypeUtil(model, _bri, _accalc.getPvSvcList());		
		/* build factorization pattern */
		_pat = new SpSymMtrxFactPattern();
		_pat.eliminate(_adj, _btu.getBuses(BusType.Reference));
		
		/* Build B' (store it already factorized) */
		_bPrime = new BPrime(_adj, bldrBp).factorize(_pat);
		
		/* Build B'' (keep the actual matrix object to allow for changes of element values) */
		_bdblprime_mtrx = new BDblPrime(_adj, bldrBpp);
		
		/* Build a list of buses with type PV */
		BusList pvbuses = SubLists.getBusSublist(_buses, 
			_btu.getBuses(BusType.PV));
		
		_varmon = new GenVarMonitor(_hotislands, _btu, _buses, _bdblprime_mtrx, _cvtpvpq, null);
		_dsmon = new DistributedSlackMonitor();
		for(Bus b : pvbuses)
			_bdblprime_mtrx.incBdiag(b.getIndex(), 1e+06f);
		
		 _vsp = new VoltageSetPoint(pvbuses, _buses, _model.getElectricalIslands().size());
		
	}
	
	private void cleanupOldResults() throws PAModelException
	{
		GenList gens = _model.getGenerators();
		Arrays.fill(gens.getP(), 0f);
		Arrays.fill(gens.getQ(), 0f);
		SVCList svcs = _model.getSVCs();
		Arrays.fill(svcs.getP(), 0f);
		Arrays.fill(svcs.getQ(), 0f);
	}

	class ActiveGenData extends Active1TData
	{
		int[] revidx;
		boolean[] inavr;
		GenList _gens;
		ActiveGenData(GenList actvgen, boolean[] inavr,
				int[] revidx, float sbase) throws PAModelException
		{
			super(_bri, actvgen, () -> actvgen.getPS(), () -> actvgen.getQS(), sbase);
			this.inavr = inavr;
			this.revidx = revidx;
			_gens = actvgen;
		}
		
		GenList getGens() {return _gens;}
		/**
		 * Toggle the AVR state of the given generator
		 * 
		 * @return true if the generate was active, false otherwise
		 */
		boolean stopAVR(Gen g)
		{
			int rx = revidx[g.getIndex()];
			if (rx == -1) return false;
			inavr[revidx[g.getIndex()]] = false;
			return true;
		}
		
		@Override
		public void applyMismatch(Mismatch pmm, Mismatch qmm) throws PAModelException
		{
			float[] p = pmm.get(), q = qmm.get();
			int[] bx = _actvgen.getBus();
			int ngen = bx.length;
			float[] pg = PAMath.mva2pu(getP(), _sbase);
			float[] qg = PAMath.mva2pu(getQ(), _sbase);
			for(int i=0; i < ngen; ++i)
			{
				int b = bx[i];
				p[b] += pg[i];
				if(!inavr[i]) 
					q[b] += qg[i];
			}
		}
	}
	
	/**
	 * Find any islands that are "hot" with at least one generator providing
	 * active power, and track those in a separeate index
	 * 
	 * As a by-product, use this time to also set up the in-service list of
	 * generators, and track which ones are regulating voltage
	 * 
	 * @throws PAModelException
	 */	
	void setupHotIslands() throws PAModelException
	{
		ElectricalIslandList islands = _model.getElectricalIslands();
		int nisl = islands.size(), nhot = 0;
		int[] idx = new int[nisl];
		
		GenList gens = _model.getGenerators();
		int ngen = gens.size(), np=0;
		int[] pidx = new int[ngen];
		boolean[] qidx = new boolean[ngen];
		int[] revidx = new int[ngen];
		Arrays.fill(revidx, -1);
		
		for(ElectricalIsland island : islands)
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
					int lx = g.getIndex();
					pidx[np] = lx;
					revidx[lx] = np;
					qidx[np++] = g.isRegKV();
				}
			}

		}
		_hotislands = SubLists.getIslandSublist(islands, Arrays.copyOf(idx, nhot));
		_actvgen = new ActiveGenData(SubLists.getGenSublist(gens, 
			Arrays.copyOf(pidx, np)), 
			Arrays.copyOf(qidx, np), revidx, _sbase);
		
	}

	FactorizedFltMatrix getBDblPrime()
	{
		if (_bDblPrime == null)
		{
			_bDblPrime = _bdblprime_mtrx.factorize(_pat);
		}
		return _bDblPrime;
	}
	
	@FunctionalInterface
	private interface GetFloat<T>
	{
		float get(T o) throws PAModelException;
	}

	Action _cvtpvpq = (b,q) ->
	{
		_bDblPrime = null;
		_btu.changeType(BusType.PQ, b.getIndex(), b.getIsland().getIndex());
		GetFloat<Gen> fv = (q > 0f) ? j -> j.getMaxQ() : j -> j.getMinQ();
		for(Gen g : b.getGenerators())
		{
			if(_actvgen.stopAVR(g))
			{
				g.setQS(fv.get(g));
			}
		}
	};
	
	/** Bus types to compare for convergence. */

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
		_qmm = new Mismatch(_bri, _btu, _ReacMismatchTypes);
		/** Convergence information for each island */
		ConvergenceList rv = new ConvergenceList(_hotislands, _btu, pmm, _qmm, _cnvtol, _cnvtol, _vm);
		/** apply voltage setpoints to vm */
		_vsp.applyToVMag(_vm);
		
		for(MismatchReporter r : _mmreport)
			r.reportBegin(_buses);
		
		boolean incomplete = true;//, dump = true;
		for(int it=0; incomplete && it < _maxit; ++it)
		{
			/* apply mismatches to both P and Q */
			applyMismatches(pmm, _qmm, _vm, _va);
			
			/* test for convergence */
			incomplete = !rv.test();
			/* distribute slack */
			_dsmon.monitor(pmm.get(), rv);
			
			/* solve a new set of voltages and angles */
			if (incomplete)
			{
				/* check for limit violations */
				_varmon.monitor(_qmm.get(), rv);
				/* check remote-monitored buses and adjust any setpoints as needed */
//				_vsp.applyRemotes(_vm, rv);
				/* correct magnitudes */
//				_bdblprime_mtrx.adjustSVC();
//				_bDblPrime = null;
				applyCorrections(_vm, _vm, getBDblPrime(), _qmm);
//				if (dump)
//				{
////					dump = false;
//					try
//					{
//						PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(String.format("/tmp/bpp-%d.csv", it))));
//						getBDblPrime().dump(_buses.getName(), pw);
//						pw.close();
//					}
//					catch(IOException ioex) {ioex.printStackTrace();}
//				}
				/* correct angles */
				applyCorrections(_va, _vm, _bPrime, pmm);
			}
			
		}
		
		for(MismatchReporter r : _mmreport)
		{
			if (r.reportLast())
			{
				r.reportMismatch(PAMath.pu2mva(pmm.get(),  _sbase), 
					PAMath.pu2mva(_qmm.get(), _sbase), _vm, PAMath.rad2deg(_va), _btu.getTypes());
			}
			r.reportEnd();
		}
		
		return rv;
	}

	void applyCorrections(float[] state, float[] vm, FactorizedFltMatrix b, Mismatch mm)
	{
		float[] m = mm.get();
		for(int bus : b.getElimBus())
			m[bus] /= vm[bus];
		float[] c = b.solve(m);
		for(int bus : b.getElimBus())
			state[bus] += c[bus];
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
		
		_accalc.calc(vm, va);
		_accalc.applyMismatch(pmm, qmm);
		
		if (!_mmreport.isEmpty())
		{
			updateResults();
			for (MismatchReporter r : _mmreport)
			{
				if (!r.reportLast())
				{
					r.reportMismatch(PAMath.pu2mva(pmm.get(), _sbase),
						PAMath.pu2mva(qmm.get(), _sbase), vm,
						PAMath.rad2deg(va), _btu.getTypes());
				}
			}
		}
	}

	public void addMismatchReporter(MismatchReporter r)
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

	/**
	 * Update all results to the model (buses and branches)
	 * @throws PAModelException
	 */
	public void updateResults() throws PAModelException
	{
		updateBusResults();
		_accalc.updateResults();
		updateVarMismatches();
		updateGenActivePower();
	}
	
	/** update active generators to their active setpoints */
	void updateGenActivePower() throws PAModelException
	{
		
		for(Gen g : _actvgen.getGens())
			g.setP(g.getPS());
		
	}

	/**
	 * Set maximum iterations (both active and reactive).  Default is 40.
	 * @param i max iteration count.
	 */
	public void setMaxIterations(int i) {_maxit = i;}
	/**
	 * Get maximum iterations.  Default is 40.
	 * @return maximum iterations
	 */
	public int getMaxInterations() {return _maxit;}
	/**
	 * Set the convergence tolerance for both active and reactive mismatches.  Default is 5.
	 * @param tol MVA value to meet or beat in order to be converged
	 */
	public void setCovergenceTolerance(float tol) {_cnvtol = PAMath.mva2pu(tol, _sbase);}
	/**
	 * Get the convergence tolerance for both active and reactive mismatches.  Default is 5.
	 * @return Convergence tolerance in MVA
	 */
	public float getConvergenceTolerance() {return PAMath.pu2mva(_cnvtol, _sbase);}
	/**
	 * Set the slack activation tolerance. If the largest active mismatch is
	 * within this value, then slack is distributed for that iteration.  Defaults to 10
	 * 
	 * @param tol
	 *            Slack activation tolerance in MW
	 */
	public void setSlackActivationTolerance(float tol)
	{
		_pdistacttol = PAMath.mva2pu(tol, _sbase);
	}
	/**
	 * Get the slack activation tolerance. If the largest active mismatch is
	 * within this value, then slack is distributed for that iteration.  Defaults to 10
	 * 
	 * @return Slack activation tolerance in MW
	 */
	public float getSlackActivationTolerance()
	{
		return PAMath.pu2mva(_pdistacttol, _sbase);
	}
	/**
	 * Set the unit active limit tolerance. Units can be available for slack
	 * distribution up to this level. Defaults to 100%
	 * 
	 * @param tol
	 *            percent of unit limit.
	 */
	public void setUnitActiveLimitTolerance(float tol) {_pdisttol = tol/100f;}
	/**
	 * Get the unit active limit tolerance. Units can be available for slack
	 * distribution up to this level. Defaults to 100%
	 * 
	 * @return percent of unit limit.
	 */
	public float getUnitActiveLimitTolerance() {return _pdisttol*100f;}
	
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
		bldr.setUnitRegOverride(false);
//		bldr.enableRCorrection(true);
		PAModel m = bldr.load();

		FDPowerFlow pf = new FDPowerFlow(m, BusRefIndex.CreateFromSingleBuses(m));
//		pf.addMismatchReporter(new DetailMismatchReporter(m, outdir, false));
		pf.addMismatchReporter(new SummaryMismatchReporter(outdir));
		pf.setMaxIterations(40);
		pf.setSlackActivationTolerance(0.5f);
		ConvergenceList results = pf.runPF();
		pf.updateResults();
		results.forEach(l -> System.out.println(l));
		
		PsmFmtExport exp = new PsmFmtExport(m, false);
		exp.export(new File("/tmp/ieee24"));
		
	}

	abstract static class VarAllocator
	{
		boolean _disable = false;
		OneTermDev _dev;
		
		VarAllocator(OneTermDev dev) {_dev = dev;}
		
		abstract float getQLim() throws PAModelException;

		/** Disable the allocator from further consideration */
		void disable() {_disable = true;}

		/** currently assigned MVAr */ 
		float getQ() throws PAModelException {return _dev.getQ();}
		
		/** currently assigned MVAr */
		void setQ(float q) throws PAModelException {_dev.setQ(q);}

		static VarAllocator Create(SVC g, float m)
		{
			return (m < 0f) ? (new VarAllocator(g)
			{
				@Override
				public float getQLim() throws PAModelException {return g.getMinQ();}
			}) : (new VarAllocator(g)
			{
				@Override
				float getQLim() throws PAModelException {return g.getMaxQ();} 

			});
		}
		
		static VarAllocator Create(Gen g, float m)
		{
			return (m < 0f) ? (new VarAllocator(g)
			{
				@Override
				public float getQLim() throws PAModelException {return g.getMinQ();}
			}) : (new VarAllocator(g)
			{
				@Override
				float getQLim() throws PAModelException {return g.getMaxQ();} 

			});
		}
	}
	
	/**
	 * Update MVAr mismatches to generators and SVC located on the PV bus.
	 * 
	 * If multiple AVR equipment is located on the same bus, we dispatch the
	 * mismatch scaled by the max var capability.
	 * 
	 * If a device hits a capability limit, then we re-calculate the
	 * distribution factors and apply the remaining
	 * 
	 * @throws PAModelException
	 */
	void updateVarMismatches() throws PAModelException
	{
		for (BusType btype : new BusType[] { BusType.PV, BusType.Reference })
		{
			for (int bx : _btu.getBuses(btype))
			{
				Bus b = _buses.get(bx);
				float m = -PAMath.pu2mva(_qmm.get(bx), _sbase);
				VarAllocator[] vm = loadAllocators(b, m);
				int n = vm.length;
				while (m != 0f)
				{
					float[] pct = calcPcts(vm, m);
					float unallocated = 0f;
					for (int i = 0; i < n; ++i)
					{
						VarAllocator a = vm[i];
						/** q max limit */
						float qm = a.getQLim();
						/** MVAr to assign */
						float qa = m * pct[i];
						/** difference from limit */
						float qd = qm - qa;
						/*
						 * If the difference has the opposite sign as the q max,
						 * then we exceeded the capability. Put the difference
						 * back on to the mismatch for now
						 */
						if (Math.signum(qm) == -1f * Math.signum(qd))
						{
							unallocated += qd;
							qa = qm;
							a.disable();
						}
						a.setQ(qa);
					}
					m = unallocated;
				}
			}
		}
	}
	/**
	 * Calculate the distribution of remaining vars based on current capability
	 * 
	 * @param vm
	 * @param m
	 * @return
	 * @throws PAModelException
	 */
	private float[] calcPcts(VarAllocator[] vm, float m) throws PAModelException
	{
		int n = vm.length;
		float[] rv = new float[n];
		float max = 0f;
		for (int i = 0; i < n; ++i)
		{
			float q = vm[i].getQLim();
			rv[i] = q;
			max += q;
		}
		for (int i = 0; i < n; ++i)
			rv[i] /= max;
		return rv;
	}
	/**
	 * Return a list of objects that can handle the var dispatch regardless of
	 * device type
	 * 
	 * @param b
	 * @param m
	 * @return
	 * @throws PAModelException
	 */
	private VarAllocator[] loadAllocators(Bus b, float m) throws PAModelException
	{
		GenList gens = b.getGenerators();
		SVCList svcs = b.getSVCs();
		int ngen = gens.size(), nsvc = svcs.size();
		VarAllocator[] rv = new VarAllocator[ngen + nsvc];
		int n = 0;
		for (Gen g : gens)
		{
			if (g.isGenerating())
			{
				VarAllocator va = VarAllocator.Create(g, m);
				if (va != null) rv[n++] = va;
			}
		}
		for (SVC g : svcs)
		{
			VarAllocator va = VarAllocator.Create(g, m);
			if (va != null) rv[n++] = va;
		}
		return Arrays.copyOf(rv, n);
	}	
	boolean isPVSvc(SVC svc) throws PAModelException
	{
		return (svc.isInService() && svc.isRegKV() && svc.getSlope() == 0f);
	}
}

