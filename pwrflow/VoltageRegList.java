package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import java.util.Arrays;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;

public class VoltageRegList extends AbstractList<com.powerdata.openpa.pwrflow.VoltageRegList.VoltageReg>
{
	public interface VoltRegAction
	{
		boolean test();
		void take();
	}


	public static class VoltageReg
	{
		VoltageRegList _list;
		int _ndx;
		VoltageReg(VoltageRegList list, int index)
		{
			_list = list;
			_ndx = index;
		}
	}
	

	float[] _vsp, _minq, _maxq;
	int[] _bus;
	VoltRegAction[][] _action;
	
	VoltageRegList(BusList buses, BusTypeUtil btypes) throws PAModelException
	{
		int[] pv = btypes.getBuses(BusType.PV);
		int[] slk = btypes.getBuses(BusType.Reference);
		int npv = pv.length, nslk = slk.length, n = npv+nslk;
		_bus = Arrays.copyOf(pv, n);
		System.arraycopy(slk, 0, _bus, npv, nslk);
		_vsp = new float[n];
		_minq = new float[n];
		_maxq = new float[n];
		
		for(int i=0; i < n; ++i)
		{
			Bus b = buses.get(_bus[i]);
			float vsp = 0f;
			int ngen = 0;
			for(Gen g : b.getGenerators())
			{
				if (unitInAVr(g))
				{
					_minq[i] += g.getMinQ();
					_maxq[i] += g.getMaxQ();
					vsp += g.getVS();
					++ngen;
				}
			}
			for(SVC s : b.getSVCs())
			{
				if (svcInAVr(s))
				{
					_minq[i] += s.getMinQ();
					_maxq[i] += s.getMaxQ();
					vsp += s.getVS();
					++ngen;
				}
			}
			_vsp[i] = vsp / (float) ngen;
		}
		
	}
	
	@Override
	public VoltageReg get(int index) {return new VoltageReg(this, index);}
	@Override
	public int size() {return _vsp.length;}

	static boolean unitInAVr(Gen g) throws PAModelException
	{
		Gen.Mode m = g.getMode();
		return !g.isOutOfSvc() && g.isRegKV() && m != Gen.Mode.PMP && m != Gen.Mode.OFF;
		
	}
	
	static boolean svcInAVr(SVC c) throws PAModelException
	{
		return !c.isOutOfSvc() && c.isRegKV() && c.getSlope() == 0f;
	}
	
}
