package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.List;
import com.powerdata.openpa.tools.GroupMap;


public class BasicBusGrpMap implements BusGrpMap
{
	
	protected WeakReference<List<int[]>> _grps = new WeakReference<>(null);
	protected WeakReference<int[]> _tokens = new WeakReference<>(null);
	protected int[] _map;
	protected int _ngrp;
	
	public BasicBusGrpMap()
	{
		_ngrp = 0;
		_map = null;
	}
	
	public BasicBusGrpMap(int[] map, int ngrp)
	{
		_map = map;
		_ngrp = ngrp;
	}	
	@Override
	public int getGrp(int index)
	{
		return _map[index];
	}

	@Override
	public List<int[]> map()
	{
		List<int[]> rv = _grps.get();
		if (rv == null)
		{
			rv = new GroupMap(_map, _ngrp);
			_grps = new WeakReference<>(rv);
		}
		return rv;
	}

	@Override
	public int size()
	{
		return _ngrp;
	}

	@Override
	public int[] getTokens()
	{
		int[] rv = _tokens.get();
		int n = size();
		if (rv == null)
		{
			rv = new int[n];
			List<int[]> l = map();
			for(int i=0; i < n; ++i)
			{
				rv[i] = l.get(i)[0];
			}
		}
		return rv;
	}
}