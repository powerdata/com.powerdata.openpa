package com.powerdata.openpa.pwrflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;

public class GenVarMonitors extends BusSetMonitor
{
	float[] _minq, _maxq;
	Supplier<float[]> _qmm, _vm, _vsp;
	BusConverter _pvcnv, _pqcnv;
	float _convtol;
	
//	ArrayList<MonTrans> _transmon = new ArrayList<>();
	
	
	@FunctionalInterface
	interface FloatFunction<R>
	{
		R apply(float v);
	}
	
//	class MonTrans implements Monitor
//	{
//		FloatFunction<Monitor> _mf;
//		int _monidx;
//		
//		MonTrans(FloatFunction<Monitor> mf, int monidx)
//		{
//			_mf = mf;
//			_monidx = monidx;
//			_transmon.add(this);
//		}		
//		@Override
//		public Monitor monitor(int bus, int i)
//		{
//			return _mf.apply(_vm.get()[bus]);
//		}
//		public int getIndex() {return _monidx;}
//	}

	abstract static class MonOut implements Monitor
	{
		float _vviol;
		MonOut(float vviol)
		{
			_vviol = vviol;
		}
	}
	
	class MonLow extends MonOut
	{
		MonLow(float vviol) {super(vviol);}
		@Override
		public Monitor monitor(int bus, int i) throws PAModelException
		{
			if(_vm.get()[bus] > _vviol)
			{
				_pvcnv.cvtType(bus, i, _qmm.get()[bus]);
				return _pvdeft;
			}
			return null;
		}
	}
	
	class MonHi extends MonOut
	{
		MonHi(float vviol) {super(vviol);}
		@Override
		public Monitor monitor(int bus, int i) throws PAModelException
		{
			if(_vm.get()[bus] > _vviol)
			{
				_pvcnv.cvtType(bus, i, _qmm.get()[bus]);
				return _pvdeft;
			}
			return null;	
		}
	}
	public interface BusConverter
	{
		void cvtType(int bus, int i, float qmm) throws PAModelException;
	}
	
	Monitor _pvdeft = new Monitor()
	{
		@Override
		public Monitor monitor(int bus, int i) throws PAModelException
		{
			float qmm = _qmm.get()[bus];
			if (bus == 140)
			{
				int xxx = 5;
			}
			if (qmm < _minq[i])
			{
				_pqcnv.cvtType(bus, i, qmm);
//				return new MonLow(_vsp.get()[i]);
//				return new MonTrans(MonLow::new, i);
			}
			else if (qmm > _maxq[i])
			{
				_pqcnv.cvtType(bus, i, qmm);
//				return new MonHi(_vsp.get()[i]);
			}
			return null;
		}
	};
	
	Monitor _refdeft = new Monitor()
	{
		@Override
		public Monitor monitor(int bus, int i)
		{
			// TODO add mechanism to pick a new reference bus 
			return null;
		}
	};

	public GenVarMonitors(BusRegQuantities breq, int nisl, 
			BusConverter pvconv, BusConverter pqconv)
	{
		super(breq.getMonBus(), breq.getMonBusIslands(), nisl, null);
		_mon = configureMonitors(breq.getMonBus().length, breq.getMonRefNdx());
		_minq = breq.getMinQ();
		_maxq = breq.getMaxQ();
		_pvcnv = pvconv;
		_pqcnv = pqconv;
	}
	
	public void setVSP(Supplier<float[]> vsp) {_vsp = vsp;}
	public void setQMM(Supplier<float[]> qmm) {_qmm = qmm;}
	public void setVM(Supplier<float[]> vm) {_vm = vm;}

	Monitor[] configureMonitors(int nmonbus, int[] refndx)
	{
		Monitor[] rv = new Monitor[nmonbus];
		Arrays.fill(rv, _pvdeft);
		for(int rn : refndx) rv[rn] = _refdeft;
		return rv;
	}
	
//	public void monitorTransition()
//	{
//		for(MonTrans tm : _transmon)
//		{
//			int i = tm.getIndex();
//			Monitor m = tm.monitor(_monbus[i], i);
//			_mon[i] = m;
//		}
//		_transmon.clear();
//	}
	
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
