package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.EnumSet;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.SpSymFltMatrix;

/**
 * Monitor generator vars and convert bus type if needed
 * 
 * @author chris@powerdata.com
 *
 *  TODO:  Implement PQ -> PV return when appropriate
 *  TODO:  Move the bus conversion logic to BDblPrime so multiple classes can make use of it
 */
public class GenVarMonitor extends BusMonitor
{
	float _sbase = 100f;
	SpSymFltMatrix _bpp;
	
	float[] _savebdiag;
	float[] _minq, _maxq;
	boolean[] _viol;
	/** Action to take to convert pv to pq */
	Action _pv2pq;
	
	/** Monitor low pv bus limits and convert if needed */
	Monitor _pvmon = (mm,i) -> 
	{
		mm = -mm;
		boolean rv = false;
		
		if(mm < _minq[i] || mm > _maxq[i])
		{
			cvtPV2PQ(i);
			_pv2pq.take(_sbus.get(_mbus[i]), mm);
			rv = true;
		}
		return rv;
	};
	
	public GenVarMonitor(ElectricalIslandList hotislands, BusTypeUtil btu, BusList buses,
			SpSymFltMatrix bpp, Action pv2pq, Action pq2pv)
			throws PAModelException
	{
		super(buses, btu, EnumSet.of(BusType.PV), hotislands);
		_bpp = bpp;
		_pv2pq = pv2pq;
		Arrays.fill(_monitors, _pvmon);
		saveOriginalB();
		configureLimits();
	}
	
	void cvtPV2PQ(int i)
	{
		/*
		 * restore B without the artifically large admittance, making it a PQ
		 * bus once again
		 */
		_bpp.getBDiag()[_mbus[i]] = _savebdiag[i];
		/* turn off monitoring for now */
		_monitors[i] = _Nomon;
	}
	
	void saveOriginalB()
	{
		int n = _mbus.length;
		_savebdiag = new float[n];
		float[] bd = _bpp.getBDiag();

		for(int i=0; i < n; ++i)
			_savebdiag[i] = bd[_mbus[i]];
	}
	
	private void configureLimits() throws PAModelException
	{
		int nbus = _mbus.length;
		_minq = new float[nbus];
		_maxq = new float[nbus];
		_viol = new boolean[nbus];
		
		for(int i=0; i < nbus; ++i)
		{
			Bus bus = _sbus.get(_mbus[i]);
			float mnq=0f, mxq=0f;
			for(Gen g : bus.getGenerators())
			{
				if(g.unitInAVR())
				{
					mnq += PAMath.mva2pu(g.getMinQ(), _sbase);
					mxq += PAMath.mva2pu(g.getMaxQ(), _sbase);
				}
			}
			for(SVC s : bus.getSVCs())
			{
				if (s.isInService() && s.isRegKV())
				{
					mnq += PAMath.mva2pu(s.getMinQ(), _sbase);
					mxq += PAMath.mva2pu(s.getMaxQ(), _sbase);
				}
			}
			_minq[i] = mnq;
			_maxq[i] = mxq;
		}
	}

	@Override
	protected boolean testIsland(ConvergenceInfo ci)
	{
		return Math.abs(ci.getWorstQ().getValue()) < 0.1f;
	}
	
	
}
