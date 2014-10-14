package com.powerdata.openpa.pwrflow;

import java.util.function.Supplier;

public class RemoteVoltageMonitors extends BusSetMonitor
{
	public interface SetpointMgr
	{
		void adjust(int isp, int bus, float dvs);
	}
	
	
	private BusTypeUtil _btype;
	private float[] _rvsp, _lastadj;
	private int[] _cbus, /** index into avr setpoint index */_isp;
	private Supplier<float[]> _vm;
	private SetpointMgr _sm;
	
	Monitor _deft = new Monitor()
	{
		@Override
		public Monitor monitor(int bus, int i)
		{
			int cb = _cbus[i];
			if (_btype.getType(cb) == BusType.PV)
			{
				float vm = _vm.get()[bus];
				float dvm = _rvsp[i] - vm - _lastadj[i];
				if (dvm != 0f)
					_sm.adjust(_isp[i], cb, dvm);
				_lastadj[i] += dvm;
			}
			return null;
		}
	};
	
	public RemoteVoltageMonitors(BusRegQuantities breg, BusTypeUtil btype, int nisl,
			Supplier<float[]> vm, SetpointMgr sp)
	{
		super(breg.getRmtRegBus(), breg.getRmtRegBusIslands(), nisl, null);
		_btype = btype;
		_rvsp = breg.getRmtVS();
		_sm = sp;
		_vm = vm;
		_cbus = breg.getRmtRegCBus();
		_isp = breg.getRmtMonIndex();
		int nmon = _monbus.length;
		_mon = new Monitor[nmon];
		for(int i=0; i < nmon; ++i)
			_mon[i] = _deft;
		_lastadj = new float[nmon];
	}
};


