package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Supplier;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.FactorizedBMatrix;
import com.powerdata.openpa.tools.PAMath;

public abstract class FDPFCore
{
	public enum Variant {BX, XB}
	Variant _variant = Variant.BX;
	int _niter = 40;
	float _ptol = 0.5f, _qtol = 0.5f;
	FactorizedBMatrix _bp;
	/** index into energized Islands */
	int[] _eindx;

	public int getMaxIterations() {return _niter;}
	public void setMaxIterations(int niter) {_niter = niter;}
	
	public Variant getVariant() {return _variant;}
	public void setVariant(Variant v) {_variant = v;}
	
	public float getMWTol() {return _ptol;}
	public void setMWTol(float mw) {_ptol = mw;}
	
	public float getMVArTol() {return _qtol;}
	public void setMVArTol(float mvar) {_qtol = mvar;}
	
	PAModel _model;
	HashSet<CorrectionsProc> _corr = new HashSet<>();
	ForkJoinPool _pool = new ForkJoinPool(2);

	public FDPFCore(PAModel m) throws PAModelException
	{
		_model = m;
		IslandList ilist = m.getIslands();
		int ni = ilist.size(), nhi=0;
		int[] is = new int[ni];
		for(int i=0; i < ni; ++i)
		{
			if (ilist.isEnergized(i))
				is[nhi++] = i;
		}
		_eindx = Arrays.copyOf(is, nhi);
		
		_corr.add(new CorrectionsProc(() -> getBp(), () -> getPmm())); 
		_corr.add(new CorrectionsProc(() -> getBpp(), () -> getQmm())); 
	}
	
	static class IslandConv
	{
		
	}
	
	static class CorrectionsProc
	{
		Supplier<FactorizedBMatrix> bm;
		Supplier<float[]> mm, state;
		
		CorrectionsProc(Supplier<FactorizedBMatrix> bm, Supplier<float[]> mm, Supplier<float[]> state)
		{
			this.bm = bm;
			this.mm = mm;
			this.state = state;
		}
		
		void apply()
		{
			float[] t = bm.get().solve(mm.get());
			float[] s = state.get();
			int n = s.length;
			for(int i=0; i < n; ++i) s[i] += t[i];
		}
	}
	
	
	
	public IslandConv[] runPF()
	{
		float[] va = getVA(), vm = getVM();
		int nhi = _eindx.length, nbus = va.length;
		IslandConv[] conv = new IslandConv[nhi];
		Arrays.fill(conv, new IslandConv());
		boolean nconv = true;
		@SuppressWarnings("serial")
		RecursiveAction _task = new RecursiveAction()
		{
			@Override
			protected void compute()
			{
				_corr.parallelStream().forEach(p -> p.apply(s);
			}
		};
				
		
		for(int i=0; nconv && i < _niter; ++i)
		{
			//TODO
//			_pool.sub
		}
		
		return conv;
	}
	
	protected FactorizedBMatrix getBp()
	{
		//TODO
		return null;
	}
	
	protected FactorizedBMatrix getBpp()
	{
		//TODO
		return null;
	}
	
	protected float[] getPmm()
	{
		//TODO
		return null;
	}
	
	protected float[] getQmm()
	{
		//TODO
		return null;
	}
	
	/**
	 * Get current bus voltage angle working array 
	 * @return current bus voltage angle working array
	 */
	abstract protected float[] getVM();
	/**
	 * Get current bus voltage angle working array
	 * 
	 * @return current bus voltage angle working array
	 */
	abstract protected float[] getVA();
	
	/**
	 * Update flows, shunts, generator actuals, and bus values back to the model
	 */
	public void updateResults()
	{
		
	}
}
