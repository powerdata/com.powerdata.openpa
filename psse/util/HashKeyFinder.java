package com.powerdata.openpa.psse.util;

import java.util.HashMap;

public class HashKeyFinder implements KeyFinder
{
	protected int _size = 0;
	protected HashMap<Long,Integer> _map;
	
	/** default constructor */
	public HashKeyFinder() {_map = new HashMap<>();}
	/**
	 * Construct a finder that can prepare memory requirements if known
	 * @param expectedSize
	 */
	public HashKeyFinder(int expectedSize)
	{
		_map = new HashMap<>(expectedSize);
	}
	
	@Override
	public HashKeyFinder map(long[] keys)
	{
		for(int i=0; i < keys.length; ++i) map(keys[i]);
		return this;
	}
	@Override
	public void map(long key)
	{
		_map.put(key, _size++);
	}
	@Override
	public int findNdx(long key)
	{
		Integer n = _map.get(key);
		return (n == null) ? -1 : n;
	}

}
