package com.powerdata.openpa.tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

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
	protected int _brcnt = 0;
	protected int _cnt[] = new int[0];

	public void ensureCapacity(int nodes, int branches)
	{
		if (nodes >= _list.length)
		{
			int l = _list.length;
			_list = Arrays.copyOf(_list, nodes*2);
			Arrays.fill(_list, l, _list.length, Empty);
			_cnt  = Arrays.copyOf(_cnt, _list.length);
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
	
	public int getBranchCount()
	{
		return _brcnt;
	}

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
	public void iterateConnections(int node)
	{
		int end = _list[node];
		while (end >= 0)
		{
			int far = _far[end];
			if (far >= 0)
			{
				System.out.printf("Node: %d, far: %d, end/2: %d%n",node,far,end/2);
				//if (!it.iterate(node, far, end / 2)) return;
			}
			end = _next[end];
		}
	}

	/**
	 * Determine all islands, and report result in IslandOrganizer.  
	 * @return
	 */
	//public IslandOrganizer findIslands()
	public void findIslands()
	{
		int ncnt = getNodeCapacity();
		BitSet seen = new BitSet(ncnt);
		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		int nrem = ncnt;
		/** map node to an island */
		int nodeIslandMap[] = new int[ncnt];
		int islandCounts[] = new int[ncnt];
		/** order nodes by island */
		int busbyisland[] = new int[ncnt];
		ArrayList<Integer> islandIndex = new ArrayList<Integer>(); 
		int nfound = 0;
		int nisland = 0;
		
		while (nrem > 0)
		{
			// start out with a new island
			int nextnode = seen.nextClearBit(0);
			stack.push(nextnode);
			seen.set(nextnode);
			islandIndex.add(nfound);
			while(!stack.isEmpty())
			{
				int p = stack.pop();
				nodeIslandMap[p] = nisland;
				islandCounts[nisland] += 1;
				busbyisland[nfound++] = p;
				--nrem;
				int end = _list[p];
				while (end >= 0)
				{
					int far = _far[end];
					if (far >= 0 && !seen.get(far))
					{
						stack.push(far);
						seen.set(far);
					}
					end = _next[end];
				}
			}
			++nisland;
		}
		System.out.printf("Island Count: %d%n", nisland);
		//return new IslandOrganizer(nodeIslandMap, busbyisland,
		//	islandIndex.toArray());
	}
	
	public int getNodeCapacity()
	{
		return _list.length;
	}
	
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
	
	public static class NodesOfBranch
	{
		public int p;
		public int q;
	}
	
	public void getNodesForBranch(int br, NodesOfBranch result)
	{
		int endq = br*2;
		int endp = endq+1;
		result.p = _far[endp];
		result.q = _far[endq];
	}

	public void getNodesForBranches(int[] br, int brofs, int brcnt,
		int[] pnode, int pnofs,
		int[] qnode, int qnofs)
	{
		for (int ibr=0; ibr < brcnt; ++ibr, ++brofs)
		{
			int endq = br[brofs]*2;
			int endp = endq+1;
			pnode[pnofs++] = _far[endp];
			qnode[qnofs++] = _far[endq];
		}
	}
	public int[] getConnectionCounts()
	{
		return _cnt;
	}
	public static void main(String args[])
	{
		LinkNet ln = new LinkNet();
		ln.addBranch(0, 1);
		ln.addBranch(0, 2);
		ln.addBranch(2, 3);
		ln.addBranch(4, 5);
		ln.addBranch(5, 6);
		
		ln.iterateConnections(2);
		ln.findIslands();
	}
}
