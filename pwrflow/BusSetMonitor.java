package com.powerdata.openpa.pwrflow;

import java.lang.ref.WeakReference;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.GroupMap;

@Deprecated
public class BusSetMonitor
{
	Monitor[] _mon;
	int[] _monbus, _isl;
	WeakReference<GroupMap> _islgrp = new WeakReference<>(null);
	int _nisl;
	

	public BusSetMonitor(int[] monbus, int[] islmap, int nhotIslands, Monitor[] mon)
	{
		_mon = mon;
		_monbus = monbus;
		
		_isl = islmap;
		_nisl = nhotIslands;
		
	}
	
	public interface Monitor
	{
		Monitor monitor(int bus, int i) throws PAModelException;
	}

	public void monitor() throws PAModelException
	{
		int n = _mon.length;
		for(int i=0; i < n; ++i)
		{
			Monitor m = _mon[i].monitor(_monbus[i], i);
			if (m != null) _mon[i] = m;
		}
	}
	
	protected int[] getBuses(int island)
	{
		GroupMap gm = _islgrp.get();
		if (gm == null)
		{
			gm = new GroupMap(_isl, _nisl);
			_islgrp = new WeakReference<>(gm);
		}
		return gm.get(island);
	}
	
	public void monitor(int island) throws PAModelException
	{
		for(int x : getBuses(island))
		{
			Monitor m = _mon[x].monitor(_monbus[x], x);
			if (m != null) _mon[x] = m;
		}
	}
	
	public int[] getBuses() {return _monbus;}

}
