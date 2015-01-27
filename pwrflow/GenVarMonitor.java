package com.powerdata.openpa.pwrflow;

import gnu.trove.map.hash.TIntIntHashMap;

import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.BasicBusGrpMap;
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.SpSymFltMatrix;

/**
 * Monitor generator vars and convert bus type if needed
 * 
 * @author chris@powerdata.com
 *
 *  TODO:  Implement PQ -> PV return when appropriate
 *  TODO:  Move the bus conversion logic to BDblPrime so multiple classes can make use of it
 */
public class GenVarMonitor
{
	float _sbase = 100f;
	@FunctionalInterface
	public interface Action
	{
		void take(Bus bus, float qinj) throws PAModelException;
	}
	
	@FunctionalInterface
	interface Monitor
	{
		boolean test(float mm, int i) throws PAModelException;
	}

	SpSymFltMatrix _bpp;
	
	/** save original value for B'' bus diagonal in order of PV Bus List */
	float[] _savebdiag;
	float[] _minq, _maxq;
	/** bus indexes of the original BusList in order of _pvbuses */
	int[] _pvidx;
	boolean[] _viol;
	BusList _pvbuses;
	Monitor[] _monitors;
	/** no monitoring */
	Monitor _nomon = (i,j) -> false;
	/** Action to take to convert pv to pq */
	Action _pv2pq;
	/** track pv bus to hot island index */
	List<int[]> _busbyisland;
	
	/** Monitor low pv bus limits and convert if needed */
	Monitor _pvmon = (mm,i) -> 
	{
		boolean rv = false;
		if(mm < _minq[i] || mm > _maxq[i])
		{
			cvtPV2PQ(i);
			_pv2pq.take(_pvbuses.get(i), mm);
			rv = true;
		}
		return rv;
	};
	
	public GenVarMonitor(SpSymFltMatrix bpp, BusList pvbuses, IslandList hotislands, Action pv2pq,
			Action pq2pv) throws PAModelException
	{
		_bpp = bpp;
		_pvbuses = pvbuses;
		/* save the original buses for each PV bus in the list */
		int nbus = pvbuses.size();
		_pvidx = new int[nbus];
		/* correlate the pvbuses with appropriate hot island */
		int nhot = hotislands.size();
		TIntIntHashMap imap = new TIntIntHashMap(nhot, 0.5f, -1, -1);
		for(int ii=0; ii < nhot; ++ii)
			imap.put(hotislands.getIndex(ii), ii);
		
		int[] bmap = new int[nbus];
		
		for(int i=0; i < nbus; ++i)
		{
			Bus b = _pvbuses.get(i);
			_pvidx[i] = b.getIndex();
			bmap[i] = imap.get(b.getIsland().getIndex());
		}
		_busbyisland = new BasicBusGrpMap(bmap, nhot).map();
		_pv2pq = pv2pq;
		saveOriginalB();
		setupMonitors();
		configureLimits();
	}
	
	void setupMonitors()
	{
		_monitors = new Monitor[_pvbuses.size()];
		Arrays.fill(_monitors, _pvmon);
	}

	void cvtPV2PQ(int i)
	{
		/*
		 * restore B without the artifically large admittance, making it a PQ
		 * bus once again
		 */
		_bpp.getBDiag()[_pvidx[i]] = _savebdiag[i];
		/* turn off monitoring for now */
		_monitors[i] = _nomon;
	}
	
	void saveOriginalB()
	{
		int n = _pvbuses.size();
		_savebdiag = new float[n];
		float[] bd = _bpp.getBDiag();

		for(int i=0; i < n; ++i)
			_savebdiag[i] = bd[_pvidx[i]];
	}
	
	private void configureLimits() throws PAModelException
	{
		int nbus = _pvbuses.size();
		_minq = new float[nbus];
		_maxq = new float[nbus];
		_viol = new boolean[nbus];
		
		for(int i=0; i < nbus; ++i)
		{
			Bus bus = _pvbuses.get(i);
			float mnq=0f, mxq=0f;
//			int bx = bus.getIndex();
			for(Gen g : bus.getGenerators())
			{
				if(g.isGenerating() && g.isRegKV())
				{
					mnq += PAMath.mva2pu(g.getMinQ(), _sbase);
					mxq += PAMath.mva2pu(g.getMaxQ(), _sbase);
				}
			}
			_minq[i] = mnq;
			_maxq[i] = mxq;
		}
	}
	
	public void monitor(Mismatch qmm, ConvergenceList rv) throws PAModelException
	{
		float[] mm = qmm.get();
		int nrv = rv.size();
		for(int irv = 0; irv < nrv; ++irv)
		{
			ConvergenceInfo ci = rv.get(irv);
			if (Math.abs(ci.getWorstQ().getValue()) < 0.1f)
			{
				for(int i : _busbyisland.get(irv))
				{
					_monitors[i].test(mm[_pvbuses.getIndex(i)], i);
				}
			}
		}
//		int n = _pvbuses.size();
//		for(int i=0; i < n; ++i)
//		{
//			_monitors[i].test(mm[_pvbuses.getIndex(i)], i);
//		}
	}
	
	
}
