package com.powerdata.openpa.tools;

import java.util.Arrays;

/**
 * A subclass of LinkNet that keeps the groups (islands) up-to-date aggressively.
 * 
 * TODO: Compare performances and just see if the additional cost of the
 * aggressive algorithm matters much. If not, then this could just be the norm
 * in the LinkNet parent
 * 
 * @author chris@powerdata.com
 *
 */
public class ConcurrentGroupLinkNet extends LinkNet
{
	int[] _bus2Island = new int[0];
	int[] _islandSize = new int[0];
	
	@Override
	public boolean ensureCapacity(int maxBusNdx, int branchCount)
	{
		boolean rv = super.ensureCapacity(maxBusNdx, branchCount);
		int newlen = _list.length;
		int oldlen = _bus2Island.length;
		if (rv && oldlen < newlen)
		{
			_bus2Island = Arrays.copyOf(_bus2Island, newlen);
			for(int i=oldlen; i < newlen; ++i)
				_bus2Island[i] = i;
			_islandSize = Arrays.copyOf(_islandSize, newlen);
			Arrays.fill(_islandSize, oldlen, newlen, 1);
		}
		return rv;
	}

	public int getGroup(int bus) {return _bus2Island[bus];}
	
	@Override
	public GroupMap findGroupMap()
	{
		int nbus = getMaxBusNdx()+1;
		int[] is = new int[nbus];
		int nisland = 0;
		for(int i=0; i < nbus; ++i)
		{
			int iz = _islandSize[i];
			if(iz > 0) is[nisland++] = iz;
		}
		return new GroupMap(_bus2Island, nisland, Arrays.copyOf(is, nisland));
	}

	@Override
	public int addBranch(int fromBusNdx, int toBusNdx)
	{
		merge(fromBusNdx, toBusNdx);
		return super.addBranch(fromBusNdx, toBusNdx);
	}

	@Override
	public boolean eliminateBranch(int brofs)
	{
		boolean rv = super.eliminateBranch(brofs);
		if(rv)
		{
			int[] buses = getBusesForBranch(brofs);
			split(buses[0], buses[1]);
		}
		return rv;
	}
	
	@Override
	public boolean restoreBranch(int brofs)
	{
		int[] buses = getBusesForBranch(brofs);
		merge(changeElimState(buses[0]), changeElimState(buses[1]));
		return super.restoreBranch(brofs);
	}

	/**
	 * Add an eliminated branch which handles all the logic, but doesn't need to merge and then back it out
	 * @param fromBusNdx
	 * @param toBusNdx
	 * @return
	 */
	public int addBranchEliminated(int fromBusNdx, int toBusNdx)
	{
		return _addBranch(fromBusNdx, toBusNdx, (ef,et) ->
		{
			_far[ef] = changeElimState(toBusNdx);;
			_far[et] = changeElimState(fromBusNdx);;
			
			++(_ecnt[fromBusNdx]);
			++(_ecnt[toBusNdx]);
		});
	}

	/**
	 * Update the bus2Island linkage array with the results of the merge.
	 * @param fbus from-side bus of the recently added branch
	 * @param tbus to-side bus of the recently added branch
	 */
	protected void merge(int fbus, int tbus)
	{
		int fi = _bus2Island[fbus];
		int ti = _bus2Island[tbus];
		
		if (fi != ti)
		{
			/*
			 * Pick an order to merging so that we try to minimize traversals
			 */
			if (_islandSize[fi] > _islandSize[ti])
			{
				int t = ti;
				ti = fi;
				fi = t;
				
				t = tbus;
				tbus = fbus;
				fbus = t;
			}
			//TODO:  Test this technique versus just a simple array return & iteration
			final int newisland = fi, oldisland = ti;
			forEachInGroup(fbus, b -> 
			{
				_bus2Island[b] = newisland;
				++(_islandSize[newisland]);
				--(_islandSize[oldisland]);
			});
			
		}
	}
	
	protected void split(int fbus, int tbus)
	{
		throw new UnsupportedOperationException(
			"com.powerdata.openpa.tools.ConcurrentGroupLInkNet.split is not yet implemented");
	}
	
}
