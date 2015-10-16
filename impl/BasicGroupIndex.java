package com.powerdata.openpa.impl;

import java.lang.ref.WeakReference;
import java.util.List;
import com.powerdata.openpa.GroupIndex;


public class BasicGroupIndex implements GroupIndex
{
	
	protected WeakReference<List<int[]>> _grps = new WeakReference<>(null);
	protected WeakReference<int[]> _tokens = new WeakReference<>(null);
	protected int[] _map;
	protected int _ngrp;
	
	public BasicGroupIndex()
	{
		_ngrp = 0;
		_map = null;
	}
	
	public BasicGroupIndex(int[] map, int ngrp)
	{
		_map = map;
		_ngrp = ngrp;
	}	
	@Override
	public int getGrp(int index)
	{
		return (index == -1) ? -1 : _map[index];
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

}