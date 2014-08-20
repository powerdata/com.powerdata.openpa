package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.impl.GenSubList;
import com.powerdata.openpa.impl.LoadSubList;
import com.powerdata.openpa.pwrflow.CalcBase.BranchComposite;
import com.powerdata.openpa.pwrflow.CalcBase.FixedShuntComposite;
import com.powerdata.openpa.tools.FactorizedBMatrix;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.SpSymBMatrix;
import com.powerdata.openpa.tools.SpSymMtrxFactPattern;

public class FDPFCore
{
	public enum Variant {BX, XB}
	Variant _variant = Variant.XB;
	float _sbase;
	
	int _niter = 40;
	float _ptol = 0.005f, _qtol = 0.005f;
	FactorizedBMatrix _bp, _bpp;
	BDblPrime _mbpp;
	
	/** index into energized Islands */
	int[] _eindx;

	public int getMaxIterations() {return _niter;}
	public void setMaxIterations(int niter) {_niter = niter;}
	
	public float getMWTol() {return _ptol;}
	public void setMWTol(float mw) {_ptol = mw;}
	
	public float getMVArTol() {return _qtol;}
	public void setMVArTol(float mvar) {_qtol = mvar;}
	
	PAModel _model;
	ForkJoinPool _pool = new ForkJoinPool();
	BusList _buses;
	
	SvcCalc _csvc;
	Set<FixedShuntCalc> _cfsh;
	HashSet<CalcBase> _cset;
	
	/** in-service generators */
	GenList _gens;
	/** in-service loads */
	LoadList _loads;

	int[] _ldbus, _genbus;
	
	BusTypeUtil _btypes;
	
	float[] _vm, _va;
	
	/** track info for each branch type */
	static class BranchRec
	{
		public BranchRec(BranchComposite comp)
		{
			_comp = comp;
		}
		BranchComposite _comp;
		int[] _netndx;
		BranchMtrxBldr _bp, _bpp;
		public BranchComposite getComp() {return _comp;}
		public void setBp(MtrxBldrBp mbp) {_bp = mbp;}
		public void setBpp(MtrxBldrBpp mbpp) {_bpp = mbpp;}
		public void setBranchIndex(int[] lndx) {_netndx = lndx;}
		public BranchMtrxBldr getBp() {return _bp;}
		public BranchMtrxBldr getBpp() {return _bpp;}
	}
	
	/** branch info by type */
	Map<ListMetaType,BranchRec> _brc = new HashMap<>();
	/** shunt info by type */
	Map<ListMetaType, FixedShuntComposite> _fsh; 
	
	
	public FDPFCore(PAModel m) throws PAModelException
	{
		_model = m;
		_sbase = m.getSBASE();
		IslandList ilist = m.getIslands();
		int ni = ilist.size(), nhi=0;
		int[] is = new int[ni];
		for(int i=0; i < ni; ++i)
		{
			if (ilist.isEnergized(i))
				is[nhi++] = i;
		}
		_eindx = Arrays.copyOf(is, nhi);
		
		BusRefIndex bri = BusRefIndex.CreateFromSingleBus(m);
		_buses = bri.getBuses();
		
		for (Entry<ListMetaType, BranchComposite> e : CalcBase
				.getBranchComposite(m, bri).entrySet())
			_brc.put(e.getKey(), new BranchRec(e.getValue()));
		
		_fsh = CalcBase.getFixedShuntComposite(m, bri);
		
		/** convenient list of calculators for parallelization */
		_cset = new HashSet<>();

		_brc.values().forEach(i -> _cset.add(i.getComp().getCalc()));
		_fsh.values().forEach(i -> _cset.add(i.getCalc()));
		
		_csvc = new SvcCalc(m.getSBASE(), bri, m.getSVCs());
		_cset.add(_csvc);
		_btypes = new BusTypeUtil(m, bri);

		GenList allgens = m.getGenerators();
		LoadList allloads = m.getLoads();
		
		_gens = new GenSubList(allgens, CalcBase.GetInService(allgens));
		_loads = new LoadSubList(allloads, CalcBase.GetInService(allloads));
		_ldbus = bri.get1TBus(_loads);
		_genbus = bri.get1TBus(_gens);
		
		configureBranchY();
		
		MtrxBldr mb = new MtrxBldr(_buses.size());
		BPrime bp = new BPrime(mb);
		bPrimeHook(bp);
		_bp = bp.factorize();
		_mbpp = new BDblPrime(mb);
	}

	@FunctionalInterface
	private interface PAFunction<R,T>
	{
		R calc(T t) throws PAModelException;
	}
	
	void configureBranchY() throws PAModelException
	{
		PAFunction<float[], BranchComposite> 
		full = t -> calcSusceptance(t), nor = t -> calcSusceptanceNoR(t),
		bpf = full, bppf = full;
		
		switch(_variant)
		{
			case XB:
				bpf = nor;
				break;
			case BX:
				bppf = nor; //TODO: Don't think this is correct.  Seems like shunts should be included
				break;
			default:
		}
		
		for(BranchRec brec : _brc.values())
		{
			BranchComposite comp = brec.getComp();
			brec.setBp(new MtrxBldrBp(comp, bpf.calc(comp)));
			brec.setBpp(new MtrxBldrBpp(comp, bppf.calc(comp)));
		}
	}
	
	float[] calcSusceptanceNoR(BranchComposite comp) throws PAModelException
	{
		float[] x = comp.getBranches().getX();
		int n = x.length;
		float[] y = new float[n];
		for(int i=0; i < n; ++i) y[i] = 1f/x[i];
		return y;
	}
	
	float[] calcSusceptance(BranchComposite comp)
	{
		float[] nb = comp.getY().im();
		int n = nb.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = -nb[i];
		return rv;
	}

	class IslandConv
	{
		int[] _pq, _pv;
		int _iter = 0, _island;
		int _worstp, _worstq;
		float _wpmm=0f, _wqmm=0f;
		boolean _pconv=false, _qconv=false;
		
		public IslandConv(int island, int[] pq, int[] pv)
		{
			_pq = pq;
			_pv = pv;
			_island = island;
		}
		
		public void test(float[] pmm, float[] qmm)
		{
			++_iter;
			_wpmm = 0f;
			_wqmm = 0f;
			for(int[] pqpv : new int[][]{_pq, _pv})
			{
				for(int p : pqpv)
				{
					float pm = Math.abs(pmm[p]);
					if (_wpmm < pm)
					{
						_worstp = p;
						_wpmm = pm;
					}
				}
			}
			
			for(int pq : _pq)
			{
				float qm = Math.abs(qmm[pq]);
				if (_wqmm < qm)
				{
					_worstq = pq;
					_wqmm = qm;
				}
			}
			_pconv = _wpmm < _ptol;
			_qconv = _wqmm < _qtol;
		}
		public boolean pConv() {return _pconv;}
		public boolean qConv() {return _qconv;}

		@Override
		public String toString()
		{
			try
			{
			return String.format("Island %d %d iters, pconv=%s qconv=%s worstp=%f at %s, worstq=%f at %s",
				_island, _iter, String.valueOf(_pconv), String.valueOf(_qconv), 
				_wpmm, _buses.get(_worstp).getName(), _wqmm, _buses.get(_worstq).getName());
			}
			catch(PAModelException e)
			{
				return "err: "+e;
			}
		}
	}

	class CorrectionsProc
	{
		Supplier<FactorizedBMatrix> bm;
		float[] state, mm;
		
		CorrectionsProc(Supplier<FactorizedBMatrix> bm, float[] mm,
				float[] state)
		{
			this.bm = bm;
			this.mm = mm;
			this.state = state;
		}
		void apply()
		{
			FactorizedBMatrix mtrx = bm.get();
			int[] belim = mtrx.getElimBus();
			for(int b : belim) mm[b] /= _vm[b];
			float[] t = mtrx.solve(mm);
			for(int b : belim) state[b] -= t[b];
		}
	}

	public IslandConv[] runPF() throws PAModelException
	{
		_va = PAMath.deg2rad(_buses.getVA());
		setupVM();
		int nhi = _eindx.length, nbus = _va.length;
		IslandConv[] convstat = new IslandConv[nhi];
		setupConvInfo(convstat);
		boolean nconv = true;
		float[] pmm = new float[nbus], qmm = new float[nbus];
		
		/* set up runnable to process voltage and angle corrections */
		HashSet<CorrectionsProc> corr = new HashSet<>();
		corr.add(new CorrectionsProc(() -> getBp(), pmm, _va)); 
		corr.add(new CorrectionsProc(() -> getBpp(), qmm, _vm));
		Runnable rcorr = () -> corr.parallelStream().forEach(j -> j.apply());

		/* runnable to calculate system flows based on current state */
		Runnable calc = () -> _cset.parallelStream().forEach(
			j -> j.calc(_va, _vm));
		
		Runnable rconv = () -> Arrays.stream(convstat)
				.parallel()
				.forEach(j -> j.test(pmm, qmm));
		
		for(int i=0; nconv && i < _niter; ++i)
		{
			/* Calculate flows */
			_pool.execute(calc);
			Arrays.fill(pmm, 0f);
			Arrays.fill(qmm, 0f);
			_pool.awaitQuiescence(1, TimeUnit.DAYS); //infinite wait
			

			/* apply mismatches from calculated and boundary values */
			applyMismatches(pmm, qmm, _va, _vm);
			mismatchHook(pmm, qmm);
			
			/* calculate convergence */
			_pool.execute(rconv);
			_pool.awaitQuiescence(1,  TimeUnit.DAYS);
			nconv = true;
			for(IslandConv ic : convstat)
				nconv &= !(ic.pConv() && ic.qConv());
			
			// update voltage and angles
			if (nconv)
			{
				_pool.execute(rcorr);
				_pool.awaitQuiescence(1, TimeUnit.DAYS);
			}			
		}
		
		return convstat;
	}

	void setupVM() throws PAModelException
	{
		_vm = PAMath.vmpu(_buses);
		int ngen = _gens.size();
		boolean[] rkv = _gens.isRegKV();
		float[] vs = _gens.getVS();
		for(int i=0; i < ngen; ++i)
		{
			if (rkv[i])
			{
				int gb = _genbus[i];
				_vm[gb] = vs[i] / _buses.get(gb).getVoltageLevel().getBaseKV();
			}
			 
		}
	}
	/** call back when b' is updated */
	protected void bPrimeHook(BPrime bp)
	{
		// default is no action
	}
	
	/** call back when b'' is updated */
	protected void bDblPrimeHook(BDblPrime _mbpp2)
	{
		// default is no action
	}

	/** call back when the mismatches are updated */
	protected void mismatchHook(float[] pmm, float[] qmm)
	{
		// Default is no action
	}

	void setupConvInfo(IslandConv[] convstat)
	{
		int n = convstat.length;
		for(int i=0; i < n; ++i)
		{
			convstat[i] = new IslandConv(i, _btypes.getBuses(BusType.PQ, i),
					_btypes.getBuses(BusType.PV, i));
		}
	}
	
	void applyMismatches(float[] pmm, float[] qmm, float[] va, float[] vm)
		throws PAModelException
	{
		for (CalcBase c : _cset)
			c.applyMismatches(pmm, qmm);
		applyLoads(pmm, qmm, vm);
		applyGens(pmm, qmm);
	}
	
	void applyLoads(float[] pmm, float[] qmm, float[] vm) 
		throws PAModelException
	{
		int nld = _loads.size();
		float[] pl = PAMath.mva2pu(_loads.getP(),_sbase),
			ql = PAMath.mva2pu(_loads.getQ(), _sbase);
		for(int i=0; i < nld; ++i)
		{
			int bx = _ldbus[i];
			pmm[bx] -= pl[i];
			qmm[bx] -= ql[i];
		}
	}
	
	void applyGens(float[] pmm, float[] qmm) 
		throws PAModelException
	{
		int ngen = _gens.size();
		float[] ps = PAMath.mva2pu(_gens.getPS(), _sbase);
		boolean[] rkv = _gens.isRegKV();
		for (int i=0; i < ngen; ++i)
		{
			int bx = _genbus[i];
			pmm[bx] -= ps[i];
			if (!rkv[i]) qmm[bx] -= PAMath.mva2pu(_gens.getQS(i), _sbase); 
		}
	}
	
	protected FactorizedBMatrix getBp()
	{
		return _bp;
	}
	
	protected FactorizedBMatrix getBpp()
	{
		if (_mbpp.processSVCs())  //indicates minor refactor
		{
			_bpp = _mbpp.factorize();
			bDblPrimeHook(_mbpp);
		}
		return _bpp;
	}
	
	/**
	 * Update flows, shunts, generator actuals, and bus values.  
	 * @throws PAModelException 
	 */
	public void updateResults() throws PAModelException
	{
		updateBusResults();
		updateShunts();
		_csvc.update();
		updateBranches();
	}


	void updateBranches() throws PAModelException
	{
		for(BranchRec br : _brc.values())
			br.getComp().getCalc().update();
	}
	
	/** update fixed shunt results to local model 
	 * @throws PAModelException */
	void updateShunts() throws PAModelException
	{
		for(FixedShuntComposite fsc : _fsh.values())
		{
			fsc.getCalc().update();
		}
	}
	
	/** update bus results to local model 
	 * @throws PAModelException */
	void updateBusResults() throws PAModelException
	{
		int nbus = _buses.size();
		for(int i=0; i < nbus; ++i)
		{
			_buses.setVM(i, _vm[i] * _buses.getVoltageLevel(i).getBaseKV());
			_buses.setVA(i, PAMath.rad2deg(_va[i]));
		}
	}

	static class DeviceB
	{
		float _btran, _bfself, _btself;
		DeviceB(float btran, float bfself, float btself)
		{
			_btran = btran;
			_bfself = bfself;
			_btself = btself;
		}
		public float getBtran() {return _btran;}
		public float getFromBself() {return _bfself;}
		public float getToBself() {return _btself;}
	}
	
	/** get the admittance matrix for a branch device */
	interface BranchMtrxBldr
	{
		DeviceB getB(int ndx);
	}

	
	static abstract class MtrxBldrBase implements BranchMtrxBldr
	{
		float[] _y;
		BranchComposite _c;
		MtrxBldrBase(BranchComposite c, float[] y)
		{
			_c = c;
			_y = y;
		}
	}
	
	/** build B' (ignoring phase shifts) */
	static class MtrxBldrBp extends MtrxBldrBase
	{
		MtrxBldrBp(BranchComposite c, float[] y) {super(c,y);}

		@Override
		public DeviceB getB(int ndx)
		{
			float y = _y[ndx];
			return new DeviceB(-y, y, y);
		}
	}

	/** build B'' */
	static class MtrxBldrBpp extends MtrxBldrBase
	{
		float[] _fbch, _tbch, _ftap, _ttap, _bmag;
		
		MtrxBldrBpp(BranchComposite c, float[] y) throws PAModelException
		{
			super(c, y);
			ACBranchList l = c.getBranches();
			_fbch = l.getFromBchg();
			_tbch = l.getToBchg();
			_ftap = l.getFromTap();
			_ttap = l.getToTap();
			_bmag = l.getBmag();
		}
		
		@Override
		public DeviceB getB(int ndx)
		{
			float y = _y[ndx], bm = _bmag[ndx], a = _ftap[ndx], b = _ttap[ndx];
			return new DeviceB(-y/(a*b), y/(b*b) - bm - _fbch[ndx],
				y/(a*a) - bm - _tbch[ndx]);
		}
		
	}
	
	class MtrxBldr
	{
		float[] bpself, bppself, bptran, bpptran;
		LinkNet net;
		
		MtrxBldr(int nbus)
		{
			int nbr = 0;
			for(BranchRec brc : _brc.values())
				nbr += brc.getComp().getBranches().size();
			
			net = new LinkNet();
			net.ensureCapacity(nbus-1, nbr);
			bpself = new float[nbus];
			bppself = new float[nbus];
			bptran = new float[nbr];
			bpptran = new float[nbr];

			for(Map.Entry<ListMetaType, BranchRec> e : _brc.entrySet())
			{
				BranchRec brec = e.getValue();

				ACBranchFlow calc = brec.getComp().getCalc();
				int[] fn = calc.getFromBus(), tn = calc.getToBus();
				int n = fn.length;
				int[] lndx = new int[n];
				brec.setBranchIndex(lndx);
				
				BranchMtrxBldr bldrbp = brec.getBp(), bldrbpp = brec.getBpp();
				
				for(int i=0; i < n; ++i)
				{
					int f = fn[i], t = tn[i];
					DeviceB bp = bldrbp.getB(i), bpp = bldrbpp.getB(i);

					int brx = net.findBranch(f, t);
					if (brx == -1)
						brx = net.addBranch(f, t);
					
					lndx[i] = brx;

					bpself[f] += bp.getFromBself();
					bpself[t] += bp.getToBself();
					bptran[brx] += bp.getBtran();
					
					bppself[f] += bpp.getFromBself();
					bppself[t] += bpp.getToBself();
					bpptran[brx] += bpp.getBtran();
				}
			}
		}
	}
	

	class BPrime extends SpSymBMatrix
	{
		BPrime(MtrxBldr bldr)
		{
			super(bldr.net, bldr.bpself, bldr.bptran, 
				_btypes.getBuses(BusType.Reference));			
		}
	}
	
	class BDblPrime extends SpSymBMatrix
	{
		SpSymMtrxFactPattern _pat = new SpSymMtrxFactPattern();
		float[] _bsvc;
		SVCState[] _state;
		
		BDblPrime(MtrxBldr bldr) throws PAModelException
		{
			super(bldr.net, bldr.bppself, bldr.bpptran, 
				_btypes.getBuses(BusType.Reference),
				_btypes.getBuses(BusType.PV));
			
			_pat.eliminate(_net, _save);
			
			int nsvc = _csvc.getBus().length;
			_bsvc = new float[_csvc.getBus().length];
			_state = new SVCState[nsvc];
			Arrays.fill(_state, SVCState.Off);
			
			processFixedShunts(_fsh.get(ListMetaType.ShuntCap));
		}


		void processFixedShunts(FixedShuntComposite comp) throws PAModelException
		{
			FixedShuntCalc calc = comp.getCalc();
			int[] nd = calc.getBus();
			float[] b = calc.getB();
			int[] insvc = calc.getInSvc();
			int n = insvc.length;
			for(int i=0; i < n; ++i)
			{
				int ix = insvc[i];
				_bdiag[nd[ix]] -= b[ix];
			}
		}
		
		public boolean processSVCs()
		{
			SVCState[] state = _csvc.getState();
			int[] nd = _csvc.getBus();
			float[] b = _csvc.getBpp();
			int n = state.length;
			boolean rv = false;
			for(int i=0; i < n; ++i)
			{
				if (_state[i] != state[i])
				{
					if (!rv) rv = true;
					_state[i] = state[i];
					float bdiff = b[i] - _bsvc[i];
					_bdiag[nd[i]] -= bdiff;
					//TODO: check the sign on this
					_bsvc[i] = b[i];
				}
			}
			return rv;
		}

		@Override
		public FactorizedBMatrix factorize()
		{
			return super.factorize(_pat);
		}
		
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
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		PAModel m = bldr.load();
		
		ArrayList<float[][]> mmlist = new ArrayList<>();
		
		FDPFCore pf = new FDPFCore(m)
		{
			@Override
			protected void mismatchHook(float[] pmm, float[] qmm)
			{
				mmlist.add(new float[][]{pmm.clone(), qmm.clone()});
			}

			@Override
			protected void bPrimeHook(BPrime bp)
			{
				try
				{
					PrintWriter bpdbg = new PrintWriter(new BufferedWriter(
						new FileWriter(new File(outdir, "bp.csv"))));
					dumpMatrix(bp, bpdbg);
					bpdbg.close();
				} catch (IOException ioe) {ioe.printStackTrace();}
			}

			private void dumpMatrix(SpSymBMatrix bp, PrintWriter pw)
			{
				try
				{
					bp.dump(_buses.getName(), pw);
				} catch(PAModelException ex) {ex.printStackTrace();}
			}

			@Override
			protected void bDblPrimeHook(BDblPrime bpp)
			{
				try
				{
					PrintWriter bpdbg = new PrintWriter(new BufferedWriter(
						new FileWriter(new File(outdir, "bpp.csv"))));
					dumpMatrix(bpp, bpdbg);
					bpdbg.close();
				} catch(IOException ioe) {ioe.printStackTrace();}
			}
			
		};
		IslandConv[] conv = pf.runPF();
		pf.updateResults();
		new ListDumper().dump(m, outdir);
		
		PrintWriter mmdbg = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outdir, "mismatch.csv"))));
		mmdbg.print("Bus");
		for(int i=0; i < mmlist.size(); ++i)
			mmdbg.format(",'P %d','Q %d'", i, i);
		mmdbg.println();
		
		for(int b=0; b < pf._buses.size(); ++b)
		{
			mmdbg.format("'%s'", pf._buses.getName(b));
			for(int i=0; i < mmlist.size(); ++i)
			{
				float[][] mm = mmlist.get(i);
				mmdbg.format(",%f,%f", mm[0][b], mm[1][b]);
			}
			mmdbg.println();
		}
		
		
		mmdbg.close();
		for(IslandConv ic : conv) System.out.println(ic);
	}
	
}


