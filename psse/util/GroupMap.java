package com.powerdata.openpa.psse.util;

import java.util.AbstractList;
import java.util.Arrays;

/** Map groups back to original list offset */
public class GroupMap extends AbstractList<int[]>
{
	protected int[] start, next, cnt;
	protected int ngrp;

	/**
	 * Set up a group map
	 * @param map Array containing the group of each item at its offset
	 * @param ngrp number of groups
	 */
	public GroupMap(int[] map, int ngrp)
	{
		this.ngrp = ngrp;
		start = new int[ngrp];
		cnt = new int[ngrp];
		next = new int[map.length]; 
		Arrays.fill(start, -1);
		for(int i=0; i < map.length; ++i)
		{
			int g = map[i];
			if (g != -1)
			{
				next[i] = start[g];
				start[g] = i;
				++cnt[g];
			}
		}
	}
	@Override
	public int[] get(int grpndx)
	{
		int[] rv = new int[cnt[grpndx]];
		fill(rv, 0, grpndx);
		return rv;
	}
	
	protected void fill(int[] rv, int ofs, int grpndx)
	{
		int s = start[grpndx];
		while (s != -1)
		{
			rv[ofs++] = s;
			s = next[s];
		}
	}
	
	@Override
	public int size() {return ngrp;}
	/**
	 * Find the number of items in a group
	 * @param grpndx Group index
	 * @return the number of items in group
	 */
	public int getCount(int grpndx) {return cnt[grpndx];}
}