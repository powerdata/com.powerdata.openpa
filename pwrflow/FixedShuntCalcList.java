package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.OneTermBaseList.OneTermBase;
import com.powerdata.openpa.tools.PAMath;

public class FixedShuntCalcList extends AbstractList<com.powerdata.openpa.pwrflow.FixedShuntCalcList.FixedShuntCalc>
{
	public class FixedShuntCalc implements OneTermBase
	{
		int _ndx;
		public FixedShuntCalc(int ndx)
		{
			_ndx = ndx;
		}
		@Override
		public int getIndex()
		{
			return _ndx;
		}

		@Override
		public Bus getBus() throws PAModelException
		{
			return FixedShuntCalcList.this.getBus(_ndx);
		}
		
		public float getQpu() {return FixedShuntCalcList.this.getQpu(_ndx);}
		public FixedShunt getShunt()
		{
			return FixedShuntCalcList.this.getShunt(_ndx);
		}
	}

	FixedShuntListIfc<? extends FixedShunt> _src;
	BusList _buses;
	int[] _buslist;
	float[] _q, _b;
	//TODO: handle SBASE more intelligently
	float _sbase = 100f;
	
	public FixedShuntCalcList(FixedShuntListIfc<? extends FixedShunt> src,
			BusRefIndex bri) throws PAModelException
	{
		_src = src;
		_buses = bri.getBuses();
		_buslist = bri.get1TBus(src);
		_b = PAMath.mva2pu(src.getB(), _sbase);
	}
	
	public FixedShunt getShunt(int ndx)
	{
		return _src.get(ndx);
	}

	public Bus getBus(int ndx) throws PAModelException
	{
		return _buses.get(_buslist[ndx]);
	}

	public float getQpu(int ndx)
	{
		return _q[ndx];
	}

	public void calc(float[] vmpu) throws PAModelException
	{
		int n = size();
		_q = new float[n];
		for(int i=0; i < n; ++i)
		{
			float vm = vmpu[_buslist[i]];
			_q[i] = _b[i] * vm * vm;
		}
	}

	public void applyMismatches(Mismatch qmm) throws PAModelException
	{
		int n = size();
		float[] mm = qmm.get();
//		int[] bndx = qmm.getBusRefIndex().get1TBus(_src);
		for(int i=0; i < n; ++i)
		{
			mm[_buslist[i]] += _q[i]; 
		}
	}

	@Override
	public int size()
	{
		return _src.size();
	}

	@Override
	public FixedShuntCalc get(int index)
	{
		return new FixedShuntCalc(index);
	}

	public void update() throws PAModelException
	{
		_src.setQ(PAMath.pu2mva(_q, _sbase));
	}
	
}
