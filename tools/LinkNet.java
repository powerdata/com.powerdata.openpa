package com.powerdata.openpa.tools;

import java.util.Arrays;

public class LinkNet implements Cloneable
{
	public static final int Empty = -1;
	
	/**
	 * Each item in this list contains an identifier to a link list stored in
	 * the set
	 */
	protected int _list[] = new int[0];
	/** next branch "end" */
	protected int _next[] = new int[0];
	/** Node index at far end of connection */
	protected int _far[] = new int[0];
	/** Number of branches so far */
	protected int _brcnt = 0;
	/** Number of connections for each node */
	protected int _cnt[] = new int[0];
	protected int _maxnode = 0;
	/**
	 * Ensure that the arrays will accommodate the number of nodes and branches
	 * specified.  The algorithm over sizes by a factor of 2 to reduce memory
	 * allocation overhead.  This is something that could probably be tuned.
	 * @param nodes
	 * @param branches
	 */
	public void ensureCapacity(int nodes, int branches)
	{
		if (nodes > _maxnode)
		{
			_maxnode = nodes;
			if (nodes >= _list.length)
			{
				int l = _list.length;
				_list = Arrays.copyOf(_list, nodes*2);
				Arrays.fill(_list, l, _list.length, Empty);
				_cnt = Arrays.copyOf(_cnt, _list.length);
			}
		}
		if (branches >= _next.length)
		{
			int l = _next.length;
			_next = Arrays.copyOf(_next, branches*2);
			Arrays.fill(_next, l, _next.length, Empty);
			_far = Arrays.copyOf(_far, _next.length);
			Arrays.fill(_far, l, _far.length, Empty);
		}
	}
	
	public int getBranchCount() { return _brcnt; }
	public int getMaxNode() { return _maxnode; }
	/**
	 * Add a branch. Does not check if a branch already exists
	 * 
	 * @param pnode
	 *            From node of branch
	 * @param qnode
	 *            To node of branch
	 * @return branch index
	 */
	public int addBranch(int pnode, int qnode)
	{
		int endp = (_brcnt++) * 2;
		int endq = endp + 1;

		ensureCapacity(Math.max(pnode, qnode),endq);
		
		_next[endp] = _list[pnode];
		_list[pnode] = endp;

		_next[endq] = _list[qnode];
		_list[qnode] = endq;
		
		_far[endp] = qnode;
		_far[endq] = pnode;
		
		++(_cnt[pnode]);
		++(_cnt[qnode]);

		return endp / 2;
	}

	/**
	 * Look for a branch between the two nodes. Will return the first found
	 * 
	 * @param pnode
	 * @param qnode
	 * @return first branch found or -1 if not found. Does not indicate if more
	 *         than one parallel branch exist
	 */
	public int findBranch(int pnode, int qnode)
	{
		int end = _list[pnode];
		while (end >= 0)
		{
			int far = _far[end];
			if (far == qnode) return end / 2;
			end = _next[end];
		}
		return -1;
	}
	/**
	 * Return an array of connected nodes.
	 * @param node
	 * @return
	 */
	public int[] findNodes(int node)
	{
		int nodes[] = new int[_cnt[node]];
		int ndx = 0;
		int end = _list[node];
		while (end >= 0)
		{
			int far = _far[end];
			if (far >= 0)
			{
				nodes[ndx++] = far;
			}
			end = _next[end];
		}
		return nodes;
	}
	/**
	 * Return an array of all valid nodes.  This array will omit any
	 * undefined nodes.
	 * @return array of nodes
	 */
	public int[] getNodes()
	{
		int nodes[] = new int[_maxnode+1];
		int nofs = 0;
		for(int i=0; i<nodes.length; i++)
		{
			if (_list[i] > -1) nodes[nofs++] = i;
		}
		return Arrays.copyOf(nodes, nofs);
	}
	/**
	 * Return the nodes for a branch.
	 * @param br
	 * @return array of two nodes
	 */
	public int[] getNodesForBranch(int br)
	{
		int endq = br*2;
		int endp = endq+1;
		return new int[] {_far[endp],_far[endq]};
	}
	/**
	 * Return an array of counts for every possible node.
	 * @return
	 */
	public int[] getConnectionCounts()
	{
		return _cnt;
	}

	/**
	 * Determine all connected groups of nodes.  
	 * @return two dim array[group][nodes]
	 */
	public int[][] findGroups()
	{
		int ncnt = getNodeCapacity();
		/** create a very simple stack */
		int stack[] = new int[ncnt];
		int stackptr = 0;
		/** track how many nodes are remaining */
		int nrem = ncnt;
		/** mark each node that has been seen */
		boolean used[] = new boolean[ncnt];
		int usedndx = 0;
		/** map node to an island */
		int nodeIslandMap[] = new int[ncnt];
		Arrays.fill(nodeIslandMap, Empty);
		/** track how many nodes are in each island */
		int islandCounts[] = new int[ncnt];
		/** number of islands found */
		int nisland = 0;
		
		while (nrem > 0)
		{
			// start out with a new island
			while(used[usedndx] && usedndx < ncnt) ++usedndx;
			int nextnode  = usedndx;
			stack[stackptr++] = nextnode;
			used[usedndx] = true;
			boolean valid = false;
			while(stackptr > 0)
			{
				--nrem;
				int p = stack[--stackptr];
				int end = _list[p];
				if (end < 0) break;
				valid = true;
				nodeIslandMap[p] = nisland;
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
			if (valid) ++nisland;
		}
		
		// Organize results into a 2d array
		int iofs[] = new int[nisland];
		int islands[][] = new int[nisland][];
		for(int i=0; i<nisland; i++) islands[i] = new int[islandCounts[i]];
		for(int i=0; i<ncnt; i++)
		{
			int indx = nodeIslandMap[i];
			if (indx >= 0) islands[indx][iofs[indx]++] = i;
		}
		return islands;
	}
	/**
	 * Return the maximum node number used.  The resulting system may
	 * be sparse, so this may not be exactly the node count.
	 * @return
	 */
	public int getNodeCapacity()
	{
		return _maxnode+1;
	}
	/**
	 * Remove a node from service.
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
				--_far[enda];
				--_far[endb];
				++(_cnt[_far[enda]]);
				++(_cnt[_far[endb]]);
			}
			else
			{
				--(_cnt[_far[enda]]);
				--(_cnt[_far[enda]]);
				++_far[enda];
				++_far[endb];
				_far[enda] *= -1;
				_far[endb] *= -1;
				
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		LinkNet rv = (LinkNet) super.clone();
		rv._far = _far.clone();
		rv._list = _list.clone();
		rv._next = _next.clone();
		rv._cnt = _cnt.clone();
		return rv;	
	}

	public LinkNet copyOf()
	{
		LinkNet rv = new LinkNet();
		rv._brcnt = _brcnt;
		int nnode = getNodeCapacity();
		int nend = _brcnt*2;
		rv.ensureCapacity(nnode,_brcnt);
		
		System.arraycopy(_far, 0, rv._far, 0, nend);
		System.arraycopy(_next, 0, rv._next, 0, nend);
		System.arraycopy(_list, 0, rv._list, 0, nnode);
		System.arraycopy(_cnt, 0, rv._cnt, 0, nnode);
		
		return rv;
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
			//ln.addBranch(1, 2);
			//ln.addBranch(3, 4);
			//ln.addBranch(3, 5);
			// get some test data and load it in
			SimpleCSV csv = new SimpleCSV("testdata/branches.csv");
			
			int eqa[] = new int[csv.getRowCount()];
			int eqb[] = new int[csv.getRowCount()];
			
			for(int r=0; r<csv.getRowCount(); r++)
			{
				eqa[r] = Integer.parseInt(csv.get("EQA", r));
				eqb[r] = Integer.parseInt(csv.get("EQB", r));
			}
			
			long st = System.currentTimeMillis();
			for(int r=0; r<csv.getRowCount(); r++)
			{
				ln.addBranch(eqa[r], eqb[r]);
			}
			long ft = System.currentTimeMillis();
			System.out.println("Time to load "+eqa.length+" branches: "+(ft-st)+"ms");
			
			st = System.currentTimeMillis();
			int nodes[] = ln.getNodes();
			ft = System.currentTimeMillis();
			System.out.println("Time to get all branches: "+(ft-st)+"ms");

			for(int i=0; i<nodes.length; i++)
			{
				int node = nodes[i];
				System.out.print("Node "+node+":");
				for(int n : ln.findNodes(node)) System.out.print(" "+n);
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
}
