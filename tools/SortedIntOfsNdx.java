package com.powerdata.openpa.tools;

import java.util.Arrays;

public class SortedIntOfsNdx
{
	long _dataofs[];
	public SortedIntOfsNdx(int data[])
	{
		int len = data.length;
		_dataofs = new long[len];
		for(int i=0; i<len; i++) _dataofs[i] = (long)data[i] << 32 | i;
		Arrays.sort(_dataofs);
	}
	public int[] getKeys()
	{
		int keys[] = new int[_dataofs.length];
		for(int i=0; i<keys.length; i++)
		{
			keys[i] = (int)(_dataofs[i] >> 32);
		}
		return keys;
	}
	public boolean containsKey(int key)
	{
		int max = _dataofs.length - 1;
		int min = 0;
		long lkey = (long)key << 32;
		long hkey = lkey | Integer.MAX_VALUE;
		while(max >= min)
		{
			int mid = (max + min) / 2;
			if (_dataofs[mid] < lkey) min = mid + 1;
			else if (_dataofs[mid] > hkey) max = mid - 1;
			else return true;
		}
		return false;
	}
	public int getOfs(int key)
	{
		int max = _dataofs.length - 1;
		int min = 0;
		long lkey = (long)key << 32;
		long hkey = lkey | Integer.MAX_VALUE;
		while(max >= min)
		{
			int mid = (max + min) / 2;
			if (_dataofs[mid] < lkey) min = mid + 1;
			else if (_dataofs[mid] > hkey) max = mid - 1;
			else return (int)(_dataofs[mid] & 0xFFFFFFFFL);
		}
		return -1;
	}
}
