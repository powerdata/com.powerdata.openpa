package com.powerdata.openpa.psse.util;

/** Provide abstraction for key to index services */
public interface KeyFinder
{
	public HashKeyFinder map(long[] keys);
	public void map(long key);
	public int findNdx(long key);
}
