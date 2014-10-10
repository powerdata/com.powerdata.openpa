package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import java.util.Arrays;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;

public abstract class ReactiveMonitorList extends AbstractList<com.powerdata.openpa.pwrflow.ReactiveMonitorList.VoltageReg>
{
	interface VoltRegAction
	{
		VoltRegAction monitor(float val, int ndx) throws PAModelException;
	}

	public static class VoltageReg
	{
		ReactiveMonitorList _list;
		int _ndx;
		VoltageReg(ReactiveMonitorList list, int index)
		{
			_list = list;
			_ndx = index;
		}
	}
	

	float[] _vsp, _minq, _maxq;
	int[] _bus;
	VoltRegAction[] _action;
	
	public void configure(BusList buses, int[] pv, int[] slk) throws PAModelException
	{
		int npv = pv.length, nslk = slk.length, n = npv+nslk;
		_bus = Arrays.copyOf(pv, n);
		System.arraycopy(slk, 0, _bus, npv, nslk);
		_vsp = new float[n];
		_minq = new float[n];
		_maxq = new float[n];
		_action = new VoltRegAction[n];
		
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
					_action[i] = loadAction(g);
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
					_action[i] = loadAction(s);
					++ngen;
				}
			}
			_vsp[i] = vsp / (float) ngen;
		}
		
	}
	
	abstract protected VoltRegAction loadAction(Gen gen);
	abstract protected VoltRegAction loadAction(SVC svc);
	
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
