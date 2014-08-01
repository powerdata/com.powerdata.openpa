package com.powerdata.openpa.util;

import java.util.Arrays;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.SVCList;

public class SvcCalc extends CalcBase
{
	PAModel _m;
	int[] _busndx;
	BusList _buses;
	SVCList _svcs;
	float[] _b, _q;
	float[] _qmin, _qmax, _slope;
	SVCState[] _state;
	
	public SvcCalc(PAModel m) throws PAModelException
	{
		_m = m;
		_buses = m.getBuses();
		_svcs = _m.getSVCs();
		setup(BusRefIndex.CreateFromConnectivityBus(m));
	}

	public SvcCalc(PAModel m, BusRefIndex bndx, SVCList svcs) throws PAModelException
	{
		_m = m;
		_buses = bndx.getBuses();
		_svcs = svcs;
		setup(bndx);
	}

	private void setup(BusRefIndex bref) throws PAModelException
	{
		_busndx = bref.get1TBus(_svcs);
		_qmin = _svcs.getMinQ();
		_qmax = _svcs.getMaxQ();
		_slope = _svcs.getSlope();
		
		int n = _svcs.size();
		for(int i=0; i < n; ++i)
		{
			float base = Math.max(_qmax[i], -_qmin[i]);
			_slope[i] /= (100f * base);
		}
	}

	public SvcCalc calc(float[] vmpu) throws PAModelException
	{
		boolean[] isregkv = _svcs.isRegKV();
		float[] vs = _svcs.getVS(),
				qs = _svcs.getQS();
		int[] insvc = getInSvc(_svcs);
		int ninsvc = insvc.length, nsvc = _svcs.size();
		
		_state = new SVCState[nsvc];
		_b = new float[nsvc];
		_q = new float[nsvc];
		Arrays.fill(_state, SVCState.Off);
		
		for(int in=0; in < ninsvc; ++in)
		{
			int i = insvc[in];
			float qmax = _qmax[i], qmin = _qmin[i];
			if (isregkv[i])
			{
				float vmsc = vs[i]; 
				float s = _slope[i];
				float vmin = vmsc - s * qmax;
				float vmax = vmsc - s * qmin;
				float vm = vmpu[_busndx[i]];
				float vmsq = vm * vm;
				if (vm < vmin)
				{
					float b = qmax / (vmin*vmin); 
					_b[i] = b;
					_q[i] = b * vmsq;
					_state[i] = SVCState.CapacitorLimit;
					
				}
				else if (vm > vmax)
				{
					float b = qmin / (vmax * vmax);
					_b[i] = b;
					_q[i] = b * vmsq;
					_state[i] = SVCState.ReactorLimit;
				}
				else
				{
					_state[i] = SVCState.Normal;
					_b[i] = -1f/s;
				}
			}
			else
			{
				_state[i] = SVCState.FixedMVAr;
				float q = qs[i];
				if (q < qmin) q = qmin;
				else if (q > qmax) q = qmax;
				_q[i] = q;
			}
		}
		
		return this;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
}
