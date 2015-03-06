package com.powerdata.openpa.pwrflow;
import java.util.AbstractList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.OneTermBaseList;
import com.powerdata.openpa.OneTermBaseList.OneTermBase;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.SVCList;

/**
 * Calculate SVC injections (and B'' entry) based on bus voltage mag.
 * TODO:  SVC logic not working, review OTS algorithm
 * @author chris@powerdata.com
 *
 */
public class SVCCalcList extends AbstractList<com.powerdata.openpa.pwrflow.SVCCalcList.SVCCalc>
	implements OneTermBaseList<com.powerdata.openpa.pwrflow.SVCCalcList.SVCCalc>
{
	public class SVCCalc implements OneTermBase
	{
		private int _ndx;

		SVCCalc(int index)
		{
			_ndx = index;
		}
		
		@Override
		public int getIndex() {return _ndx;}
		
		
		@Override
		/**
		 * Return the connected bus.  Can be either connectivity bus or topological bus
		 * based on configuration at construction
		 */
		public Bus getBus() throws PAModelException
		{
			return SVCCalcList.this.getBus(_ndx);
		}
		
		/**
		 * Return the regulated bus.  Can be either connectivity or single-bus depending on configuration
		 * @return Regulated bus
		 */
		public Bus getRegBus()
		{
			return SVCCalcList.this.getRegBus(_ndx);
		}

		/**
		 * Get the calculated state of the SVC
		 * @return state of the SVC at the given bus voltage
		 */
		public SVCState getState()
		{
			return SVCCalcList.this.getState(_ndx);
		}

		/**
		 * Get the calculated reactive injection in MVAr
		 * @return calculated reactive power (MVAr)
		 */
		public float getQpu()
		{
			return SVCCalcList.this.getQpu(_ndx);
		}
		/**
		 * Return a suseptance value suitable for B'' (this is calculated at the
		 * same time as the injection)
		 */
		public float getBpp()
		{
			return SVCCalcList.this.getBpp(_ndx);
		}
		
		/**
		 * Get the backing SVC object
		 * @return SVC
		 */
		public SVC getSVC()
		{
			return SVCCalcList.this.getSVC(_ndx);
		}
	}

	SVCList _svcs;
	BusList _buses;
	int[] _busidx, _regidx;
	float[] _b, _q;
	SVCState[] _state;
	float _sbase = 100f;

	/**
	 * Create a new calculation list
	 * 
	 * @param svcs
	 *            Model SVC's
	 * @param bri
	 *            Bus reference index used to enforce correct Bus representation
	 *            (either connectivity or single-bus)
	 * @throws PAModelException 
	 */
	public SVCCalcList(SVCList svcs, BusRefIndex bri) throws PAModelException
	{
		_svcs = svcs;
		_buses = bri.getBuses();
		_busidx = bri.get1TBus(svcs);
		_regidx = bri.mapBusFcn(svcs, i -> svcs.getRegBus(i));
	}
	
	public SVC getSVC(int ndx)
	{
		return _svcs.get(ndx);
	}

	/**
	 * Get the calculated state of the SVC
	 * @return state of the SVC at the given bus voltage
	 */
	public SVCState getState(int ndx)
	{
		return _state[ndx];
	}

	/**
	 * Return the regulated bus.  Can be either connectivity or single-bus depending on configuration
	 * @return Regulated bus
	 */
	public Bus getRegBus(int ndx)
	{
		return _buses.get(_regidx[ndx]);
	}

	/**
	 * Return a suseptance value suitable for B'' (per-unit) (this is calculated at the
	 * same time as the injection)
	 */
	public float getBpp(int ndx)
	{
		return _b[ndx];
	}

	/**
	 * Get the calculated reactive injection in MVAr
	 * @return calculated reactive power (MVAr)
	 */
	public float getQpu(int ndx)
	{
		return PAMath.pu2mva(_q[ndx], _sbase);
	}

	/**
	 * Return the connected bus.  Can be either connectivity bus or topological bus
	 * based on configuration at construction
	 */
	public Bus getBus(int ndx)
	{
		return _buses.get(_busidx[ndx]);
	}

	@Override
	public SVCCalc get(int index)
	{
		return new SVCCalc(index);
	}

	@Override
	public int size()
	{
		return _svcs.size();
	}
	
	/**
	 * Calculate SVC values
	 * @param vmpu Per-unit solve voltage magnitude
	 * @throws PAModelException 
	 */
	public void calc(float[] vmpu) throws PAModelException
	{
		//TODO:  handle remote regulation
		
		int n = size();
		_b = new float[n];
		_q = new float[n];
		_state = new SVCState[n];
		for(int i=0; i < n; ++i)
		{
			SVCCalc calc = get(i);
			SVC svc = calc.getSVC();

			float qmax = svc.getMaxQ(),
				  qmin = svc.getMinQ();
			float s = svc.getSlope();
			float vmsc = svc.getVS() / svc.getBus().getVoltageLevel().getBaseKV();
			float vmin = vmsc - s * qmax;
			float vmax = vmsc - s * qmin;
			float vm = vmpu[_busidx[i]];
			float vmsq = vm * vm;
			//TODO:  verify the per-unit conversion, is this on 100-mva, or max limit like slope?
			float bcap = PAMath.mva2pu(qmax / (vmin * vmin), _sbase);
			float breac = PAMath.mva2pu(qmin / (vmax * vmax), _sbase);
			if(svc.isRegKV() && svc.getSlope() > 0f)
			{
				if(vm < vmin)
				{
					/* at capacitive limit */
					_state[i] = SVCState.CapacitorLimit;
					_b[i] = bcap;
					_q[i] = bcap * vmsq;
				}
				else if (vm > vmax)
				{
					/* at reactive limit */
					_state[i] = SVCState.ReactorLimit;
					_b[i] = breac;
					_q[i] = breac * vmsq;
				}
				else
				{
					_state[i] = SVCState.Normal;
//					_b[i] = -1f/s;
					_b[i] = 0f;
					_q[i] = (vmsc - vm) / (100f*s);
					System.err.format("SVC: %f\n", _q[i]);
				}
			}
			else
			{
				_state[i] = SVCState.FixedMVAr;
				float qs = svc.getQS()/_sbase;
				_q[i] = (qs > 0f) ? Math.min(qs, bcap * vmsq) : Math.max(qs,  breac * vmsq);
			}
		}
	}

	public void applyMismatches(Mismatch qmm)
	{
		float[] m = qmm.get();
		int n = size();
		for(int i=0; i < n; ++i)
			m[_busidx[i]] += _q[i];
	}

	public void update() throws PAModelException
	{
		_svcs.setQ(PAMath.pu2mva(_q, _sbase));
	}
}
