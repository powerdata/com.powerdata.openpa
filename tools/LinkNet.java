package com.powerdata.openpa.tools;

import java.util.Arrays;

public class LinkNet
{
	public static final int Empty = -1;
	public static final int NoNode = -2;
	
	/**
	 * Each item in this list contains an identifier to a link list stored in
	 * the set
	 */
	protected int _list[] = new int[0];
	/** next branch "end" */
	protected int _next[] = new int[0];
	/** Bus index at far end of connection */
	protected int _far[] = new int[0];
	/** Number of branches so far */
	protected int _brcnt = 0;
	/** Number of connections for each bus */
	protected int _cnt[] = new int[0];
	/** Maximum bus index plus 1 */
	protected int _maxBusNdx = 0;

	/** Explicitly add buses to the system so that isolated buses are tracked */
	public void addBuses(int[] vbuses)
	{
		for (int v : vbuses) _list[v] = Empty;
	}
	/** Explicitly add buses to the system so that isolated buses are tracked */
	public void addBuses(int startofs, int count)
	{
		for (int i=0; i < count; ++i, ++startofs)
			_list[startofs] = Empty;
	}
	/** Explicitly add buses to the system so that isolated buses are tracked */
	public void addBus(int bus)
	{
		_list[bus] = Empty;
	}
	/**
	 * Ensure there is enough storage for the number of buses and branches
	 * specified.  The algorithm over sizes by a factor of 2 to reduce memory
	 * allocation overhead.  This check will be performed automatically
	 * as needed if the caller can not predict the sizes ahead of time.
	 * 
	 * @param maxBusNdx Maximum bus number to be used.
	 * @param branchCount Maximum number of branches.
	 */
	public void ensureCapacity(int maxBusNdx, int branchCount)
	{
		if (++maxBusNdx > _maxBusNdx)
		{
			_maxBusNdx = maxBusNdx;
			if (maxBusNdx >= _list.length)
			{
				int l = _list.length;
				_list = Arrays.copyOf(_list, maxBusNdx*2);
				Arrays.fill(_list, l, _list.length, NoNode);
				_cnt = Arrays.copyOf(_cnt, _list.length);
			}
		}
		if (branchCount >= _next.length)
		{
			int l = _next.length;
			_next = Arrays.copyOf(_next, branchCount*2);
			Arrays.fill(_next, l, _next.length, Empty);
			_far = Arrays.copyOf(_far, _next.length);
			Arrays.fill(_far, l, _far.length, Empty);
		}
	}
	/**
	 * Return the number of branches in the LinkNet.
	 * @return Number of branches.
	 */
	public int getBranchCount() { return _brcnt; }
	/**
	 * Return the maximum bus index used plus 1.
	 * @return Maximum bus index number plus 1.
	 */
	public int getMaxBusNdx() { return _maxBusNdx; }
	/**
	 * Add a branch. Does not check if a branch already exists
	 * 
	 * @param fromBusNdx
	 *            From bus index of branch
	 * @param toBusNdx
	 *            To bus index of branch
	 * @return branch index
	 */
	public int addBranch(int fromBusNdx, int toBusNdx)
	{
		int endp = (_brcnt++) * 2;
		int endq = endp + 1;

		ensureCapacity(Math.max(fromBusNdx, toBusNdx),endq);
		
		_next[endp] = _list[fromBusNdx];
		_list[fromBusNdx] = endp;

		_next[endq] = _list[toBusNdx];
		_list[toBusNdx] = endq;
		
		_far[endp] = toBusNdx;
		_far[endq] = fromBusNdx;
		
		++(_cnt[fromBusNdx]);
		++(_cnt[toBusNdx]);

		return endp / 2;
	}

	/**
	 * Look for a branch between the two buses. Will return the first found
	 * 
	 * @param fromBusNdx From bus index
	 * @param toBusNdx   To bus index
	 * @return first branch found or -1 if not found. Does not indicate if more
	 *         than one parallel branch exist
	 */
	public int findBranch(int fromBusNdx, int toBusNdx)
	{
		int end = _list[fromBusNdx];
		while (end >= 0)
		{
			int far = _far[end];
			if (far == toBusNdx) return end / 2;
			end = _next[end];
		}
		return -1;
	}
	/**
	 * Return the number of connections for a bus.
	 * @param busNdx
	 * @return Number of connections.
	 */
	public int getConnectionCount(int busNdx) { return _cnt[busNdx]; }
	/**
	 * Return all of the branches for a specific bus.  This list
	 * will be in the same order as the array of connected buses
	 * returned by findBuses().
	 * 
	 * @param busNdx Starting bus.
	 * @return Array of branches.
	 */
	public int[] findBranches(int busNdx)
	{
		int branches[] = new int[_cnt[busNdx]];
		int ndx = 0;
		int end = _list[busNdx];
		while (end >= 0)
		{
			branches[ndx++] = end / 2;
			end = _next[end];
		}
		return branches;
	}
	/**
	 * Return the buses connected to the specified bus.
	 * @param busNdx Bus to find connections for.
	 * @return Array of connected buses.
	 */
	public int[] findBuses(int busNdx)
	{
		int buses[] = new int[_cnt[busNdx]];
		int ndx = 0;
		int end = _list[busNdx];
		while (end >= 0)
		{
			int far = _far[end];
			if (far >= 0)
			{
				buses[ndx++] = far;
			}
			end = _next[end];
		}
		return buses;
	}
	/**
	 * Return both buses and branches in a single call.
	 * @return array of nodes at index 0, and branches at index 1
	 */
	public int[][] findConnections(int busNdx)
	{
		int buses[] = new int[_cnt[busNdx]];
		int branches[] = new int[_cnt[busNdx]];
		int ndx = 0;
		int end = _list[busNdx];
		while (end >= 0)
		{
			int far = _far[end];
			if (far >= 0)
			{
				buses[ndx] = far;
				branches[ndx++] = end / 2;
			}
			end = _next[end];
		}
		return new int[][] {buses, branches};
	}
	/**
	 * Return an array of all valid bus indexes.  This array will omit any
	 * undefined bus indexes.
	 * @return array of bus indexes.
	 */
	public int[] getAllBuses()
	{
		int buses[] = new int[_maxBusNdx+1];
		int bofs = 0;
		for(int i=0; i<buses.length; i++)
		{
			if (_list[i] > -1) buses[bofs++] = i;
		}
		return Arrays.copyOf(buses, bofs);
	}
	/**
	 * Return the buses for a branch.
	 * @param br
	 * @return array of two buses
	 */
	public int[] getBusesForBranch(int br)
	{
		int endq = br*2;
		int endp = endq+1;
		return new int[] {_far[endp],_far[endq]};
	}

	/**
	 * Determine all connected groups of buses.
	 * @return two dimensional array[group][buses]
	 */
	public int[][] findGroups()
	{
		int bcnt = getMaxBusNdx();
		/** create a very simple stack */
		int stack[] = new int[bcnt];
		int stackptr = 0;
		/** track how many buses are remaining */
		int brem = bcnt;
		/** mark each bus that has been seen */
		boolean used[] = new boolean[bcnt];
		for (int i = 0; i < bcnt; ++i)
		{
			if (_list[i] == NoNode)
			{
				used[i] = true;
				--brem;
			}
		}
		int usedndx = 0;
		/** map bus to an island */
		int busIslandMap[] = new int[bcnt];
		Arrays.fill(busIslandMap, Empty);
		/** track how many buses are in each island */
		int islandCounts[] = new int[bcnt];
		/** number of islands found */
		int nisland = 0;
		
		while (brem > 0)
		{
			// start out with a new island
			while(usedndx < bcnt && used[usedndx]) ++usedndx;
			int nextbus  = usedndx;
			stack[stackptr++] = nextbus;
			used[usedndx] = true;
			while(stackptr > 0)
			{
				--brem;
				int p = stack[--stackptr];
				int end = _list[p];
				busIslandMap[p] = nisland;
				islandCounts[nisland] += 1;
				while (end >= 0)
				{
					int far = _far[end];
					if (far >= 0 && !used[far])
					{
						stack[stackptr++] = far;
						used[far] = true;
					}
					end = _next[end];
				}
			}
			++nisland;
		}
		
		// Organize results into a 2d array
		int iofs[] = new int[nisland];
		int islands[][] = new int[nisland][];
		for(int i=0; i<nisland; i++) islands[i] = new int[islandCounts[i]];
		for(int i=0; i<bcnt; i++)
		{
			int indx = busIslandMap[i];
			if (indx >= 0) islands[indx][iofs[indx]++] = i;
		}
		return islands;
	}
	/**
	 * Remove a branch from service.
	 * @param brofs
	 * @param eliminate
	 */
	public void eliminateBranch(int brofs, boolean eliminate)
	{
		int enda = brofs*2;
		boolean eliminated = _far[enda] < 0;
		if (eliminate != eliminated)
		{
			int endb = enda+1;
			if (eliminated)
			{
				_far[enda] *= -1;
				_far[endb] *= -1;
				_far[enda] -= 2;
				_far[endb] -= 2;
				++(_cnt[_far[enda]]);
				++(_cnt[_far[endb]]);
			}
			else
			{
				--(_cnt[_far[enda]]);
				--(_cnt[_far[endb]]);
				_far[enda] += 2;
				_far[endb] += 2;
				_far[enda] *= -1;
				_far[endb] *= -1;
				
			}
		}
	}
	/** Permanently remove a branch */
	
	public LinkNet() {}
	
	/**
	 * Make a copy of a LinkNet
	 * @param src
	 */
	public LinkNet(LinkNet src)
	{
		_brcnt = src._brcnt;
		_cnt = src._cnt.clone();
		_far = src._far.clone();
		_list = src._list.clone();
		_maxBusNdx = src._maxBusNdx;
		_next = src._next.clone();
	}

	/**
	 * Just for unit testing.
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{			
			LinkNet ln = new LinkNet();
			ln.ensureCapacity(500000, 50000);
			//ln.addBranch(1, 2);
			//ln.addBranch(3, 4);
			//ln.addBranch(3, 5);
			// get some test data and load it in
			SimpleCSV eqcsv = new SimpleCSV("testdata/db/Branches.csv");
			
			int eqa[] = eqcsv.getInts("I");
			int eqb[] = eqcsv.getInts("J");
			
			SimpleCSV xfrcsv = new SimpleCSV("testdata/db/Transformers.csv");
			
			int xfri[] = xfrcsv.getInts("I");
			int xfrj[] = xfrcsv.getInts("J");
			int xfrk[] = xfrcsv.getInts("K");
			
			long st = System.currentTimeMillis();
			int count = eqa.length;
			for(int r=0; r<count; r++) { ln.addBranch(eqa[r], eqb[r]); }
			count = xfri.length;
			for(int r=0; r<count; r++)
			{
				ln.addBranch(xfri[r], xfrj[r]);
				if (xfrk[r] != 0) ln.addBranch(xfri[r], xfrk[r]);
			}
			long ft = System.currentTimeMillis();
			System.out.println("Time to load "+(eqa.length+xfri.length)+" branches: "+(ft-st)+"ms");
			
			st = System.currentTimeMillis();
			int buses[] = ln.getAllBuses();
			ft = System.currentTimeMillis();
			System.out.println("Time to get all buses: "+(ft-st)+"ms");

			for(int i=0; i<buses.length; i++)
			{
				int b = buses[i];
				System.out.print("Bus "+b+":");
				for(int n : ln.findBuses(b)) System.out.print(" "+n);
				System.out.println("");
			}
			st = System.currentTimeMillis();
			int islands[][] = ln.findGroups();
			ft = System.currentTimeMillis();
			System.out.println("Time to get all islands: "+(ft-st)+"ms");
			for(int i=0; i<islands.length; i++)
			{
				System.out.print("Island "+i+":");
				for(int n : islands[i]) System.out.print(" "+n);
				System.out.println("");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e);
		}
	}
	public int[] getConnectionCounts()
	{
		return _cnt.clone();
	}
}
