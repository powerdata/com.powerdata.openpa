package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.tools.PAMath;

public class BusRegQuantities
{
	int[] _monbus, _mbislref, _refx;
	int[] _rregbus, _rregcbus, _rregimon;
	float[] _vsp, _minq, _maxq, _rmtvsp;
	BusRefIndex _bri;
	int _nremote = 0;
	
	@FunctionalInterface
	private interface PASupplier<R>
	{
		R get() throws PAModelException;
	}
	
	
	public BusRegQuantities(PAModel m, BusRefIndex bri, int[] eindx, int[] pv, 
			int[] ref) throws PAModelException
	{
		int[] rvisland = new int[m.getIslands().size()];
		Arrays.fill(rvisland, -1);
		for(int i=0; i < eindx.length; ++i)
			rvisland[eindx[i]] = i;
		
		BusList buses = bri.getBuses();
		int npv = pv.length, nref = ref.length, nmon = npv + nref;
		_monbus = new int[nmon];
		_mbislref = new int[nmon]; 
		_refx = new int[nref];
		
		_minq = new float[nmon];
		_maxq = new float[nmon];
		
		/* oversize the remote arrays, correct later */
		_vsp = new float[nmon];
		int[] nunit = new int[nmon];
		_rregbus = new int[nmon];
		_rregcbus = new int[nmon];
		_rmtvsp = new float[nmon];
		_rregimon = new int[nmon];
		
		
		int i=0;
		for(int[] list : new int[][]{pv,ref})
		{
			for(int b : list)
			{
				_monbus[i] = b;
				_mbislref[i] = rvisland[buses.getIsland(b).getIndex()];
				for(Gen g : buses.getGenerators(b))
				{
					if (GenVarMonitors.unitInAVr(g))
					{
						process(i, b,buses.getByBus(g.getRegBus()), 
							g.getMinQ(), g.getMaxQ(), g.getVS(), nunit);
					}
				}
				for(SVC s : buses.getSVCs(b))
				{
					if (GenVarMonitors.svcInAVr(s))
					{
						process(i, b,buses.getByBus(s.getRegBus()), 
							s.getMinQ(), s.getMaxQ(), s.getVS(), nunit);
					}
				}
				++i;
			}
		}
		
		for(int j=0, k=npv; j < nref; ++j, ++k)
			_refx[j] = k;
		
		_bri = bri;
		
		_rregbus = Arrays.copyOf(_rregbus, _nremote);
		_rregcbus = Arrays.copyOf(_rregcbus, _nremote);
		_rregimon = Arrays.copyOf(_rregimon, _nremote);
		_rmtvsp = Arrays.copyOf(_rmtvsp, _nremote);

		for(int j=0; j < nmon; ++j)
		{
			int n = nunit[j];
			if (n != 0)
				_vsp[j] /= (float) n;
		}

		for(int j=0; j < _nremote; ++j)
		{
			_rmtvsp[j] /= (float) nunit[_rregimon[j]];
		}
		
		_minq = PAMath.mva2pu(_minq, 100f);
		_maxq = PAMath.mva2pu(_maxq, 100f);
		
	}	
	

	void process(int imon, int cbus, Bus reg, float minQ, float maxQ, 
			float vs, int[] nunit) throws PAModelException
	{
		int r = reg.getIndex();
		_minq[imon] += minQ;
		_maxq[imon] += maxQ;
		vs /= reg.getVoltageLevel().getBaseKV();
		if (r == cbus)
		{
			_vsp[imon] += vs;
		}
		else
		{
			_rregbus[_nremote] = r;
			_rregcbus[_nremote] = cbus;
			_rregimon[_nremote] = imon;
			_rmtvsp[_nremote++] += vs;
			_vsp[imon] += 1f;
		}
		++(nunit[imon]);
	}


	/** get var monitored buses */
	public int[] getMonBus() {return _monbus;}
	/** islands of var-monitored buses */
	public int[] getMonBusIslands() {return _mbislref;}
	/** locate reference buses in monitored bus list */
	public int[] getMonRefNdx() {return _refx;}
	/** local voltage setpoints (in monitored bus order) */
	public float[] getLclVS() {return _vsp;}
	/** list of remote regulated buses */
	public int[] getRmtRegBus() {return _rregbus;}
	/** return connectivity buses for units with remote regulation */
	public int[] getRmtRegCBus() {return _rregcbus;}
	/** index from the remote entry to the monitored bus */
	public int[] getRmtMonIndex() {return _rregimon;}
	/** islands of regulated buses */
	public int[] getRmtRegBusIslands()
	{
		int[] rv = new int[_nremote];
		for(int i=0; i < _nremote; ++i)
			rv[i] = _mbislref[_rregimon[i]];
		return rv;
	}
	/** get remote bus voltage setpoints */
	public float[] getRmtVS() {return _rmtvsp;}
	/** get minimum bus var limit (monitored bus order)*/
	public float[] getMinQ() {return _minq;}
	/** get maximum bus var limit (monitored bus order)*/
	public float[] getMaxQ() {return _maxq;}
}
