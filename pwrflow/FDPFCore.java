package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.TwoTermDev.Side;
import com.powerdata.openpa.impl.GenSubList;
import com.powerdata.openpa.impl.GroupMap;
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
	
	int _niter = 65;
	float _ptol = 0.005f, _qtol = 0.005f;
	boolean _enatapadj = false;
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
	
	float[] _vm, _vmc, _va;
	
	/** list of adjustable taps */
	TapAdj[] _tadj;
	/** keep handle of transformers */
	TransformerRec _tlist;
	
	/** track info for each branch type */
	static class BranchRec
	{
		public BranchRec(BranchComposite comp)
		{
			_comp = comp;
		}
		BranchComposite _comp;
		int[] _netndx;
		BranchElemBldr _bp, _bpp;
		public BranchComposite getComp() {return _comp;}
		public void setBp(ElemBldrBp mbp) {_bp = mbp;}
		public void setBpp(ElemBldrBpp mbpp) {_bpp = mbpp;}
		public void setBranchIndex(int[] lndx) {_netndx = lndx;}
		public int[] getBranchIndex() {return _netndx;}
		public BranchElemBldr getBp() {return _bp;}
		public BranchElemBldr getBpp() {return _bpp;}
		
		public void saveBpp(DeviceB bppelem, int index){};
		public DeviceB diffBpp(DeviceB newelem, int index){return null;} 
	}
	
	class TransformerRec extends BranchRec
	{
		float[] _btran, _bsf, _bst;
		int[] _regbus;
		
		public TransformerRec(BranchComposite comp, BusRefIndex bri) throws PAModelException
		{
			super(comp);
			TransformerList tlist = _model.getTransformers();
			int n = tlist.size();
			_btran = new float[n];
			_bsf = new float[n];
			_bst = new float[n];
			_regbus = bri.mapBusFcn(tlist, (i,j) -> i.getRegBus(j));
		}

		@Override
		public void saveBpp(DeviceB bbr, int index)
		{
			_btran[index] = bbr.getBtran();
			_bsf[index] = bbr.getFromBself();
			_bst[index] = bbr.getToBself();
		}

		@Override
		public DeviceB diffBpp(DeviceB newb, int index)
		{
			DeviceB rv = new DeviceBTx(_btran[index]-newb.getBtran(),
				_bsf[index] - newb.getFromBself(),
				_bst[index] - newb.getToBself());
			_btran[index] += rv.getBtran();
			_bsf[index] += rv.getFromBself();
			_bst[index] += rv.getToBself();
			return rv;
		}
		
		public int[] getRegBus() {return _regbus;}
	}
	
	/** branch info by type */
	Map<ListMetaType,BranchRec> _brc = new HashMap<>();
	/** shunt info by type */
	Map<ListMetaType, FixedShuntComposite> _fsh; 
	FDPFVoltageRegList _monitors;

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
		{
			BranchComposite c = e.getValue();
			BranchRec v = null;
			switch (e.getKey())
			{
				case Transformer:
					_tlist = new TransformerRec(c, bri);
					v = _tlist;
					break;
				default:
					v = new BranchRec(c);
			}
			_brc.put(e.getKey(),v); 
		}
		
		_fsh = CalcBase.getFixedShuntComposite(m, bri);
		
		/** convenient list of calculators for parallelization */
		_cset = new HashSet<>();

		_brc.values().forEach(i -> _cset.add(i.getComp().getCalc()));
		_fsh.values().forEach(i -> _cset.add(i.getCalc()));

		GroupMap svcpv = configureSVC();
		int[] svcpvb = svcpv.get(1), oos = svcpv.get(0);
		
		_csvc = new SvcCalc(m.getSBASE(), bri, oos, m.getSVCs());
		_cset.add(_csvc);
		_btypes = new BusTypeUtil(m, bri, svcpvb);

		LoadList allloads = m.getLoads();
		
		findOperatingGenerators();
		_loads = new LoadSubList(allloads, CalcBase.GetInService(allloads));
		_ldbus = bri.get1TBus(_loads);
		_genbus = bri.get1TBus(_gens);
		
		configureBranchY();
		
		int[] ref = _btypes.getBuses(BusType.Reference);
		int[] pv = _btypes.getBuses(BusType.PV);
		MtrxBldr mb = new MtrxBldr(_buses.size());
		BPrime bp = new BPrime(mb);
		SpSymMtrxFactPattern p = bp.savePattern(ref);
		_bp = bp.factorizeFromPattern();
		bPrimeHook(bp);
		_mbpp = new BDblPrime(mb, ref, pv);
		_mbpp.savePattern(p);
		
		if (_enatapadj) buildTapAdj();
		
		_monitors = new FDPFVoltageRegList(pv, ref);
		
	}
	
	void findOperatingGenerators() throws PAModelException
	{
		GenList list = _model.getGenerators();
		int n = list.size();
		int[] x = new int[n];
		int cnt = 0;
		for(int i=0; i < n; ++i)
		{
			Gen g = list.get(i);
			if (!g.isOutOfSvc() && g.getMode() != Gen.Mode.OFF)
				x[cnt++] = i;
		}
		_gens = new GenSubList(list, Arrays.copyOf(x, cnt));
	}

	void buildTapAdj() throws PAModelException
	{
		int[] rbus = _tlist.getRegBus();
		TransformerList tfms = _model.getTransformers();
		TapAdj[] res = new TapAdj[tfms.size()];
		Arrays.fill(res, TapAdj.NoAdj);
		int[] txinsvc = _tlist.getComp().getCalc().getInSvc();
		int cnt = 0, svccnt=txinsvc.length;
		for(int i=0; i < svccnt; ++i)
		{
			Transformer t = tfms.get(txinsvc[i]);
			if (t.hasLTC() && t.isRegEnabled())
			{
				int r = rbus[t.getIndex()];
				if (_buses.getIsland(r).isEnergized())
				{
					res[cnt++] = (t.getRegSide() == Side.From) ? new TapAdjFrom(
							t, r) : new TapAdjTo(t, r);
				}
			}
		}
		_tadj = Arrays.copyOf(res, cnt);
	}
	
	GroupMap configureSVC() throws PAModelException
	{
		SVCList svcs = _model.getSVCs();
		int nsvc = svcs.size();
		int[] map = new int[nsvc];
		for(int i=0; i < nsvc; ++i)
		{
			if (!svcs.isOutOfSvc(i) && svcs.getSlope(i) <= 0f)
				map[i] = 1;
		}
		return new GroupMap(map, 2);
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
				bppf = nor;
				break;
			default:
		}
		
		for(BranchRec brec : _brc.values())
		{
			BranchComposite comp = brec.getComp();
			brec.setBp(new ElemBldrBp(comp, bpf.calc(comp)));
			brec.setBpp(new ElemBldrBpp(comp, bppf.calc(comp)));
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
		int _iter = -1, _island;
		int _worstp=-1, _worstq=-1;
		float _wpmm=0f, _wqmm=0f;
		boolean _pconv=false, _qconv=false, _fail=false;
		
		public IslandConv(int island) throws PAModelException
		{
			_island = island;
		}
		
		public void test(float[] pmm, float[] qmm)
		{
			int[] pq = _btypes.getBuses(BusType.PQ, _island),
				pv = _btypes.getBuses(BusType.PV, _island);
			++_iter;
			_wpmm = 0f;
			_wqmm = 0f;
			for(int[] pqpv : new int[][]{pq, pv})
			{
				for(int p : pqpv)
				{
					float pm = pmm[p];
					if (!Float.isFinite(pm))
					{
						_fail = true;
						return;
					}
					pm = Math.abs(pm);
					if (_wpmm < pm)
					{
						_worstp = p;
						_wpmm = pm;
					}
				}
			}
			
			for(int bpq : pq)
			{
				float qm = qmm[bpq];
				if (!Float.isFinite(qm))
				{
					_fail = true;
					return;
				}
				qm = Math.abs(qm);
				if (_wqmm < qm)
				{
					_worstq = bpq;
					_wqmm = qm;
				}
			}
			_pconv = _wpmm < _ptol;
			_qconv = _wqmm < _qtol;
		}
		public boolean pConv() {return _pconv;}
		public boolean qConv() {return _qconv;}
		public boolean fail() {return _fail;}
		public int getWorstP() {return _worstp;}
		public int getWorstQ() {return _worstq;}
		public float getWorstPmm() {return _wpmm;}
		public float getWorstQmm() {return _wqmm;}

		@Override
		public String toString()
		{
			try
			{
				return String.format(
					"%sIsland %d %d iters, pconv=%s qconv=%s worstp=%f MW at %s, worstq=%f MVAr at %s",
					_fail?"[FAIL] ":"",
					_island, _iter, String.valueOf(_pconv), 
					String.valueOf(_qconv), _wpmm * 100f,
					(_worstp == -1) ? "[Not Solved]" : _buses.get(_worstp).getName(), _wqmm * 100f, 
					(_worstq == -1) ? "[Not Solved]" : _buses.get(_worstq).getName());
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
		void apply(float[] vm)
		{
			FactorizedBMatrix mtrx = bm.get();
			int[] belim = mtrx.getElimBus();
			for(int b : belim) mm[b] /= vm[b];
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
		boolean nconv = true, nfail = true;
		float[] pmm = new float[nbus], qmm = new float[nbus];
		
		/* set up runnable to process voltage and angle corrections */
		HashSet<CorrectionsProc> corr = new HashSet<>();
		corr.add(new CorrectionsProc(() -> getBp(), pmm, _va)); 
		corr.add(new CorrectionsProc(() -> getBpp(), qmm, _vm));
		Runnable rcorr = () -> corr.stream().forEach(j -> j.apply(_vmc));

		/* runnable to calculate system flows based on current state */
		Runnable calc = () -> _cset.parallelStream().forEach(
			j -> j.calc(_va, _vm));
		
		Runnable rconv = () -> Arrays.stream(convstat)
				.parallel()
				.forEach(j -> j.test(pmm, qmm));
		
		for(int i=0; nconv && nfail && i < _niter; ++i)
		{
			/* Calculate flows */
//			_pool.execute(calc);
			_cset.stream().forEach(
				j -> j.calc(_va, _vm));
			Arrays.fill(pmm, 0f);
			Arrays.fill(qmm, 0f);
//			_pool.awaitQuiescence(1, TimeUnit.DAYS); //infinite wait
			

			/* apply mismatches from calculated and boundary values */
			applyMismatches(pmm, qmm, _va, _vm);
			/* calculate convergence */
			
//			_pool.execute(rconv);
//			_pool.awaitQuiescence(1,  TimeUnit.DAYS);
			Arrays.stream(convstat)
			.forEach(j -> j.test(pmm, qmm));			
			nconv = false;
			for(IslandConv ic : convstat)
			{
				nfail |= !ic.fail();
				nconv |= !(ic.pConv() && ic.qConv());
			}
			
			// update voltage and angles
			if (nconv && nfail)
			{
				if (_enatapadj && i > 0) adjustTransformerTaps(convstat);
				if (i > 0) _monitors.monitor(qmm);
				_vmc = _vm.clone();
				corr.stream().sequential().forEach(j -> j.apply(_vmc));
//				_pool.execute(rcorr);
//				_pool.awaitQuiescence(1, TimeUnit.DAYS);
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
				if (_buses.getIsland(gb).isEnergized())
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
	protected void mismatchHook(float[] vm, float[] va, BusType[] type, float[] pmm, float[] qmm)
	{
		// Default is no action
	}

	void setupConvInfo(IslandConv[] convstat) throws PAModelException
	{
		int n = convstat.length;
		for(int i=0; i < n; ++i)
		{
			convstat[i] = new IslandConv(_eindx[i]);
		}
	}
	
	void applyMismatches(float[] pmm, float[] qmm, float[] va, float[] vm)
		throws PAModelException
	{
		for (CalcBase c : _cset)
			c.applyMismatches(pmm, qmm);
		applyLoads(pmm, qmm, vm);
		applyGens(pmm, qmm);
		mismatchHook(vm, va, _btypes.getTypes(), pmm, qmm);
	}
	
	interface TapAdj
	{
		boolean adjust() throws PAModelException;
		int getIsland();
		static public final TapAdj NoAdj = new TapAdj()
		{
			@Override
			public boolean adjust() throws PAModelException
			{
				return false;
			}

			@Override
			public int getIsland()
			{
				return -1;
			}
		};
	}
	
	abstract class TapAdjBase implements TapAdj
	{
		int _tx, _rbus;
		int _fbus, _tbus;
		int _island;
		float _minkv, _maxkv;
		TapAdjBase(Transformer t, int rbus) throws PAModelException
		{
			_tx = t.getIndex();
			_rbus = rbus;
			Bus b = _buses.get(rbus);
			_island = _eindx[b.getIsland().getIndex()];
			ACBranchFlow bc = _tlist.getComp().getCalc();
			_fbus = bc.getFromBus()[_tx];
			_tbus = bc.getToBus()[_tx];
			float rbase = b.getVoltageLevel().getBaseKV();
			_minkv = t.getMinKV()/rbase;
			_maxkv = t.getMaxKV()/rbase;
		}
		@Override
		public int getIsland()
		{
			return _island;
		}
		
	}

	class TapAdjFrom extends TapAdjBase
	{
		float _step = 0f;
		TapAdjFrom(Transformer t, int rbus) throws PAModelException
		{
			super(t, rbus);
			_step = t.getFromStepSize();
		}

		@Override
		public boolean adjust() throws PAModelException
		{
			float adj = 0, vm = _vm[_rbus];
			Transformer t = _model.getTransformers().get(_tx);
			// BranchElemBldr bpp = _tlist.getBpp();
			if (vm < _minkv) adj = _step;
			if (vm > _maxkv) adj = -_step;
			if (adj != 0f)
			{
				float a = t.getFromTap()/* , b = t.getToTap() */;
				float nval = a + adj, ntap = t.getFromMinTap(), xtap = t
						.getFromMaxTap();
				if (nval < ntap) nval = ntap;
				if (nval > xtap) nval = xtap;
				if (a != nval)
				{
					System.out.format("ftap %s: %f -> %f\n", t.getName(), t.getFromTap(), nval);
					t.setFromTap(nval);
					return true;
				}
			}
				//				DeviceB diff = _tlist.diffBpp(bpp.getB(_tx), _tx);
//				_mbpp.incBoffdiag(_tlist.getBranchIndex()[_tx], diff.getBtran());
//				_mbpp.incBdiag(_tbus, diff.getToBself());
			return false;
		}
		
	}
	
	class TapAdjTo extends TapAdjBase
	{
		float _step = 0f;
		TapAdjTo(Transformer t, int rbus) throws PAModelException
		{
			super(t, rbus);
			_step = t.getToStepSize();
		}

		@Override
		public boolean adjust() throws PAModelException
		{
			float adj = 0, vm = _vm[_rbus];
			Transformer t = _model.getTransformers().get(_tx);
			// BranchElemBldr bpp = _tlist.getBpp();
			if (vm < _minkv) adj = _step;
			if (vm > _maxkv) adj = -_step;
			if (adj != 0f)
			{
				float a = t.getFromTap()/* , b = t.getToTap() */;
				float nval = a + adj, ntap = t.getFromMinTap(), xtap = t
						.getFromMaxTap();
				if (nval < ntap) nval = ntap;
				if (nval > xtap) nval = xtap;
				if (a != nval)
				{
					System.out.format("ftap %s: %f -> %f\n", t.getName(), t.getFromTap(), nval);
					t.setFromTap(nval);
					return true;
				}
			}
			//				DeviceB diff = _tlist.diffBpp(bpp.getB(_tx), _tx);
//				_mbpp.incBoffdiag(_tlist.getBranchIndex()[_tx], diff.getBtran());
//				_mbpp.incBdiag(_fbus, diff.getFromBself());
			return false;
		}
		
	}
	
	
	void adjustTransformerTaps(IslandConv[] convstat) throws PAModelException
	{
		for(TapAdj ta : _tadj)
		{
			ta.adjust();
		}
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
		for (int i=0; i < ngen; ++i)
		{
			int bx = _genbus[i];
			pmm[bx] -= ps[i];
			if (_btypes.getType(bx) == BusType.PQ)
			{
				float qmmt = PAMath.mva2pu(_gens.getQS(i), _sbase);
				System.err.format("Load QS of %f from %s (%s mode %s)\n", qmmt, _buses.get(bx).getName(),
					_gens.getName(i), _gens.getMode(i).toString());
				qmm[bx] -= PAMath.mva2pu(_gens.getQS(i), _sbase);
			}
		}
	}
	
	protected FactorizedBMatrix getBp()
	{
		return _bp;
	}
	
	protected FactorizedBMatrix getBpp()
	{
		if (_mbpp.processSVCs() || _bpp == null)
		{
			_bpp = _mbpp.factorizeFromPattern();
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

	interface DeviceB
	{
		float getBtran();
		float getFromBself();
		float getToBself();
	}
	
	static class DeviceBLn implements DeviceB
	{
		float _y;
		public DeviceBLn(float y) {_y = y;}
		@Override
		public float getBtran() {return -_y;}
		@Override
		public float getFromBself() {return _y;}
		@Override
		public float getToBself() {return _y;}
	}
	
	static class DeviceBTx implements DeviceB
	{
		float _btran, _bfself, _btself;
		DeviceBTx(float btran, float bfself, float btself)
		{
			_btran = btran;
			_bfself = bfself;
			_btself = btself;
		}
		@Override
		public float getBtran() {return _btran;}
		@Override
		public float getFromBself() {return _bfself;}
		@Override
		public float getToBself() {return _btself;}
	}
	
	/** build branch admittance matrix elements */
	interface BranchElemBldr
	{
		DeviceB getB(int ndx);
	}

	
	static abstract class ElemBldrBase implements BranchElemBldr
	{
		float[] _y;
		BranchComposite _c;
		ElemBldrBase(BranchComposite c, float[] y)
		{
			_c = c;
			_y = y;
		}
	}
	
	/** build B' (ignoring phase shifts) */
	static class ElemBldrBp extends ElemBldrBase
	{
		ElemBldrBp(BranchComposite c, float[] y) {super(c,y);}

		@Override
		public DeviceB getB(int ndx)
		{
			float y = _y[ndx];
			return new DeviceBLn(y);
		}
	}

	/** build B'' */
	static class ElemBldrBpp extends ElemBldrBase
	{
		float[] _fbch, _tbch, _ftap, _ttap, _bmag;
		
		ElemBldrBpp(BranchComposite c, float[] y) throws PAModelException
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
			return new DeviceBTx(-y/(a*b), y/(b*b) - bm - _fbch[ndx],
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
				
				BranchElemBldr bldrbp = brec.getBp(), bldrbpp = brec.getBpp();
				
				for (int i = 0; i < n; ++i)
				{
					int f = fn[i], t = tn[i];
					if (f != t)
					{
						DeviceB bp = bldrbp.getB(i), bpp = bldrbpp.getB(i);
						brec.saveBpp(bpp, i);
						int brx = net.findBranch(f, t);
						if (brx == -1) brx = net.addBranch(f, t);
						lndx[i] = brx;
						bpself[f] += bp.getFromBself();
						bpself[t] += bp.getToBself();
						bptran[brx] += bp.getBtran();
						bppself[f] += bpp.getFromBself();
						bppself[t] += bpp.getToBself();
						bpptran[brx] += bpp.getBtran();
					}
					else
					{
						lndx[i] = -1;
					}
				}
			}
		}
	}
	
	class BPrime extends SpSymBMatrix
	{
		BPrime(MtrxBldr bldr)
		{
			super(bldr.net, bldr.bpself, bldr.bptran);
		}
	}	
	
	class BDblPrime extends SpSymBMatrix
	{
		float[] _bsvc;
		SVCState[] _state;
		/** keep track of the original diagonal values for monitored buses*/
		float[] _orgdiag;
		
		BDblPrime(MtrxBldr bldr, int[] ref, int[] pv) throws PAModelException
		{
			super(bldr.net, bldr.bppself, bldr.bpptran); 
			
			int nsvc = _csvc.getBus().length;
			_bsvc = new float[_csvc.getBus().length];
			_state = new SVCState[nsvc];
			Arrays.fill(_state, SVCState.Off);
			
			processPVBuses(pv);
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
		
		void processPVBuses(int[] monitoredBuses)
		{
			int n = monitoredBuses.length;
			_orgdiag = new float[n];
			for(int i=0; i < n; ++i)
			{
				int p = monitoredBuses[i];
				_orgdiag[i] = _bdiag[p];
				_bdiag[p] = 1e+06f;
			}
		}

		/** convert monitored bus type */
		public void convertToPQ(int iMon, int bus)
		{
			_bdiag[bus] = _orgdiag[iMon];
			_bpp = null;
		}
		
		/** restore to PV behavior */
		public void restoreToPV(int bus)
		{
			_bdiag[bus] = 1e+06f;
			_bpp = null;
		}

	}
	
	private interface FloatFunction<R>
	{
		R apply(float val);
	}
	class FDPFVoltageRegList extends ReactiveMonitorList
	{
		VoltRegAction _deft = new VoltRegAction()
		{
			@Override
			public VoltRegAction monitor(float qmm, int i) throws PAModelException
			{
				if (qmm < _minq[i])
					return procViol(i, MonitorBelow::new);
				else if (qmm > _maxq[i])
					return procViol(i, MonitorAbove::new);
				return null;
			}
			
			VoltRegAction procViol(int i, FloatFunction<VoltRegAction> ctr) throws PAModelException
			{
				int b = _bus[i];
				_btypes.changeType(BusType.PQ, b, _buses.getIsland(b).getIndex());
				_mbpp.convertToPQ(i, b);
				return ctr.apply(_vm[b]);
			}
		};
		
		class MonitorBelow implements VoltRegAction
		{
			float _lowvm;
			MonitorBelow(float vmviol)
			{
				_lowvm = vmviol;
			}
			@Override
			public VoltRegAction monitor(float qmm, int i) throws PAModelException
			{
				int b = _bus[i];
				if (_vm[b] > _lowvm)
				{
					_btypes.changeType(BusType.PV, b, _buses.getIsland(b).getIndex());
					_mbpp.restoreToPV(b);
					return _deft;
				}
				return null;
			}
		}
		
		class MonitorAbove implements VoltRegAction
		{
			float _hivm;
			MonitorAbove(float vmviol)
			{
				_hivm = vmviol;
			}
			@Override
			public VoltRegAction monitor(float qmm, int i) throws PAModelException
			{
				int b = _bus[i];
				if (_vm[_bus[i]] < _hivm)
				{
					_btypes.changeType(BusType.PV, b, _buses.getIsland(b).getIndex());
					_mbpp.restoreToPV(b);
					return _deft;
				}
				return null;
			}
		}
		
		FDPFVoltageRegList(int[] pv, int[] ref) throws PAModelException
		{
			super();
			configure(_buses, pv, ref);
		}

		@Override
		protected VoltRegAction loadAction(Gen gen)
		{
			return _deft;
		}

		@Override
		protected VoltRegAction loadAction(SVC svc)
		{
			return _deft;
		}
		
		public void monitor(float[] qmm) throws PAModelException
		{
			int n = size();
			for(int i=0; i < n; ++i)
			{
				_action[i].monitor(qmm[_bus[i]], i);
			}
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
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.0001f);
		bldr.enableRCorrection(true);
		bldr.setUnitRegOverride(true);
//		bldr.setBadXLimit(2f);
		PAModel m = bldr.load();
		class mminfo
		{
			float[] vm, va, pmm, qmm;
			BusType[] type;
			mminfo(float[] vm, float[] va, float[] pmm, float[] qmm, BusType[] type)
			{
				this.vm = vm;
				this.va = va;
				this.pmm = pmm;
				this.qmm = qmm;
				this.type = type;
			}
		}
		ArrayList<mminfo> mmlist = new ArrayList<>();
		System.err.println("Load PF");
		FDPFCore pf = new FDPFCore(m)
		{
			@Override
			protected void mismatchHook(float[] vm, float[] va, BusType[] type, float[] pmm, float[] qmm)
			{
				mmlist.add(new mminfo(vm.clone(), va.clone(), pmm.clone(), qmm.clone(), type));
			}

//			@Override
//			protected void bPrimeHook(BPrime bp)
//			{
//				try
//				{
//					PrintWriter bpdbg = new PrintWriter(new BufferedWriter(
//						new FileWriter(new File(outdir, "bp.csv"))));
//					dumpMatrix(bp, bpdbg);
//					bpdbg.close();
//
//					PrintWriter fbpdbg = new PrintWriter(new BufferedWriter(
//						new FileWriter(new File(outdir, "fbp.csv"))));
//					dumpFactMatrix(_bp, fbpdbg);
//					fbpdbg.close();
//				} catch (IOException ioe) {ioe.printStackTrace();}
//			}
//
//			private void dumpMatrix(SpSymBMatrix bp, PrintWriter pw)
//			{
//				try
//				{
//					bp.dump(_buses.getName(), pw);
//				} catch(PAModelException ex) {ex.printStackTrace();}
//			}
//
//			@Override
//			protected void bDblPrimeHook(BDblPrime bpp)
//			{
//				try
//				{
//					PrintWriter bpdbg = new PrintWriter(new BufferedWriter(
//						new FileWriter(new File(outdir, "bpp.csv"))));
//					dumpMatrix(bpp, bpdbg);
//					bpdbg.close();
//					
//					PrintWriter fbpdbg = new PrintWriter(new BufferedWriter(
//						new FileWriter(new File(outdir, "fbpp.csv"))));
//					dumpFactMatrix(_bpp, fbpdbg);
//					fbpdbg.close();
//					
//				} catch(IOException ioe) {ioe.printStackTrace();}
//			}
//
//			private void dumpFactMatrix(FactorizedBMatrix bp, PrintWriter pw)
//			{
//				try
//				{
//					bp.dump(_buses.getName(), pw);
//				}
//				catch (PAModelException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			
		};
//		FDPFCore pf = new FDPFCore(m);
		System.err.println("Run PF");
		IslandConv[] conv = pf.runPF();
		System.err.println("Done");
		pf.updateResults();
		ListDumper ld = new ListDumper();

		for(ACBranchList list : m.getACBranches())
			ld.dumpList(new File(outdir, list.getListMeta().toString()+".csv"), list);
		ld.dumpList(new File(outdir, "Gen.csv"), m.getGenerators());
		ld.dumpList(new File(outdir, "SVC.csv"), m.getSVCs());
		
		PrintWriter mmdbg = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outdir, "mismatch.csv"))));
		mmdbg.print("Bus,Island");
		for(int i=0; i < mmlist.size(); ++i)
			mmdbg.format(",'T %d','VA %d','VM %d','P %d','Q %d'", i, i, i, i, i);
		mmdbg.println();
		
		for(int b=0; b < pf._buses.size(); ++b)
		{
			mmdbg.format("'%s',%d", pf._buses.getName(b), pf._buses.getIsland(b).getIndex());
			for(int i=0; i < mmlist.size(); ++i)
			{
				mminfo mm = mmlist.get(i);
				mmdbg.format(",%s,%f,%f,%f,%f", mm.type[b].toString(),
					PAMath.rad2deg(mm.va[b]), mm.vm[b],
					PAMath.pu2mva(mm.pmm[b], 100f),
					PAMath.pu2mva(mm.qmm[b], 100f));
			}
			mmdbg.println();
		}
		
		
		mmdbg.close();
		for(IslandConv ic : conv) System.out.println(ic);
	}
	
}



