package com.powerdata.openpa.tools;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import com.powerdata.pd3.utils.SortedIntOfsNdx;

public abstract class SNdxKeyOfs 
{
	public abstract int size();
	public abstract boolean containsKey(int key);
	/** get offset for the key, returns -1 if not available */
	public abstract int getOffset(int key);
	public abstract int[] getOffsets(int[] keys);
	public abstract int[] getKeys();

	public static SNdxKeyOfs Create(int[] keys)
	{
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int k : keys)
		{
			if (k > max) max = k;
			if (k < min) min = k;
		}
		int len = max - min + 1;
		float ratio = keys.length / (float)len;
		// this is the best if the ratios are good or the size is small
		if (len < 1000 || ratio > 0.5) return new OfsNdx(min,max,keys);
		// this one is very fast, but breaks down for super large indexes
		if (keys.length < 1000000) return new TroveNdx(keys);
		// this one is reasonable for any size
		return new SortNdx(keys);
	}
	public int getKey(int ndx)
	{
		return getKeys()[ndx];
	}
}


class SortNdx extends SNdxKeyOfs
{
	SortedIntOfsNdx _ndxToOfs;
	int _size;
	WeakReference<int[]> _keys = new WeakReference<>(null);
	
	public SortNdx(int[] keys)
	{
		_ndxToOfs = new SortedIntOfsNdx(keys);
		_size = keys.length;
	}

	@Override
	public int size()
	{
		return _size;
	}

	@Override
	public boolean containsKey(int key)
	{
		return _ndxToOfs.containsKey(key);
	}
	
	@Override
	public int getOffset(int key)
	{
		return _ndxToOfs.getOfs(key);
	}

	@Override
	public int[] getOffsets(int[] keys)
	{
		int n = keys.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = _ndxToOfs.getOfs(keys[i]);
		return rv;
	}

	@Override
	public int[] getKeys()
	{
		int[] keys = _keys.get();
		if (keys == null)
		{
			keys = _ndxToOfs.getKeys();
			_keys = new WeakReference<>(keys);
		}
		return keys;
	}

}

class TroveNdx extends SNdxKeyOfs
{
	TIntIntMap _keyndx;
	WeakReference<int[]> _ofs = new WeakReference<>(null);
	
	public TroveNdx(int[] keys)
	{
		int nkey = keys.length;
		_keyndx = new TIntIntHashMap(nkey, 0.5f, -1, -1);
		for(int i=0; i < nkey; ++i)
			_keyndx.put(keys[i], i);
	}

	@Override
	public int size()
	{
		return _keyndx.size();
	}

	@Override
	public boolean containsKey(int key)
	{
		return _keyndx.containsKey(key);
	}

	@Override
	public int getOffset(int key)
	{
		return _keyndx.get(key);
	}

	@Override
	public int[] getOffsets(int[] keys)
	{
		int n = keys.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = _keyndx.get(keys[i]);
		return rv;
	}

	@Override
	public int[] getKeys()
	{
		return _keyndx.keys();
	}

}

class OfsNdx extends SNdxKeyOfs
{
	int _offsets[];
	WeakReference<int[]> _keys = new WeakReference<>(null);
	int _minkey, _len;
	
	public OfsNdx(int minkey, int maxkey, int keys[])
	{
		_minkey = minkey;
		_len = maxkey - minkey + 1;
		_offsets = new int[_len];
		Arrays.fill(_offsets, -1);
		for(int i=0; i < keys.length; ++i)
			_offsets[keys[i]-minkey] = i;
	}
	@Override
	public int size() {return _len;}

	@Override
	public boolean containsKey(int key)
	{
		return (key >= _minkey && _offsets[key - _minkey] != -1);
	}

	@Override
	public int[] getKeys()
	{
		int[] keys = _keys.get();
		if (keys == null)
		{
			keys = new int[_len];
			int ofs = 0;
			for (int i=0; i < _offsets.length; ++i)
				if (_offsets[i] != -1) keys[ofs++] = i+_minkey;
			_keys = new WeakReference<>(keys);
		}
		return keys;
	}

	@Override
	public int getOffset(int key)
	{
		return (key < _minkey)?-1:(_offsets[key-_minkey]);
	}

	@Override
	public int[] getOffsets(int[] keys)
	{
		int n = keys.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getOffset(keys[i]);
		return rv;
	}
}

