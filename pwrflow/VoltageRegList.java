package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import java.util.Arrays;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.tools.PAMath;
@Deprecated
public abstract class VoltageRegList extends AbstractList<com.powerdata.openpa.pwrflow.VoltageRegList.VoltageReg>
{
	interface Monitor
	{
		Monitor monitor(float val, int ndx) throws PAModelException;
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
		
		Monitor getMonitor() {return _list.getMonitor(_ndx);}
	}
	

	float[] _minq, _maxq;
	int[] _bus;
	Monitor[] _action;
	
	public Monitor getMonitor(int ndx) {return _action[ndx];}
	
	public void configure(BusList buses, int[] pv, int[] slk) throws PAModelException
	{
		int npv = pv.length, nslk = slk.length, n = npv+nslk;
		_bus = Arrays.copyOf(pv, n);
		System.arraycopy(slk, 0, _bus, npv, nslk);
//		_vsp = new float[n];
		_minq = new float[n];
		_maxq = new float[n];
		_action = new Monitor[n];
		
//		for(int i=0; i < n; ++i)
//		{
//			Bus b = buses.get(_bus[i]);
////			float vsp = 0f;
////			int ngen = 0;
//			for(Gen g : b.getGenerators())
//			{
//				if (unitInAVr(g))
//				{
//					_minq[i] += PAMath.mva2pu(g.getMinQ(), 100f);
//					_maxq[i] += PAMath.mva2pu(g.getMaxQ(), 100f);
////					vsp += g.getVS();
////					++ngen;
//				}
//			}
//			for(SVC s : b.getSVCs())
//			{
//				if (svcInAVr(s))
//				{
//					_minq[i] += PAMath.mva2pu(s.getMinQ(), 100f);
//					_maxq[i] += PAMath.mva2pu(s.getMaxQ(), 100f);
////					vsp += s.getVS();
////					++ngen;
//				}
//			}
//			//_vsp[i] = vsp / (((float) ngen)*b.getVoltageLevel().getBaseKV());
//			_action[i] = (i < npv) ? loadActionPV(i) : loadActionRef(i);
//		}
		
	}
	
	abstract protected Monitor loadActionPV(int index);
	abstract protected Monitor loadActionRef(int index);
	
	@Override
	public VoltageReg get(int index) {return new VoltageReg(this, index);}
	@Override
	public int size() {return _minq.length;}

}
