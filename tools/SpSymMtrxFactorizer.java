package com.powerdata.openpa.tools;

import java.util.Arrays;

/**
 * Symmetric (Sparse) Matrix factorization base.
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class SpSymMtrxFactorizer
{
	protected int[]	/** eliminated node order */_elimndorder	= new int[0], _ep = new int[0],
			_eq = new int[0], _elimbrorder = new int[0];
	protected int	_elimbrcnt		= 0, _iord=0;

	/**
	 * Called during elimination when a new node is being eliminated. Subclass
	 * should implement to provide appropriate services
	 * 
	 * @param elimnode
	 *            Node being eliminated
	 * @param cnd
	 *            connected node indexes
	 * @param cedge
	 * 		      connected edge indexes
	 */
	protected abstract void elimStart(int elimnode, int[] cnd, int[] cedge);
	/**
	 * Called during elimination to post processing
	 */
	protected abstract void elimStop();
	/**
	 * Called to allow subclass to manage mutual connections.
	 * 
	 * @param imut
	 *            index of the mutual record in operation
	 * @param adjbr
	 *            off-diagonal mutual connection
	 * @param targbr
	 *            id of target branch
	 */
	protected abstract void mutual(int imut, int adjbr, int targbr);

	/**
	 * Called to allow subclass to know when data stored in parallel to
	 * elimination needs to be of a certain size
	 * 
	 * @param newsize
	 */
	protected int ensureCapacity(int newsize)
	{
		if (_ep.length <= newsize)
		{
			newsize *= 2;
			_ep = Arrays.copyOf(_ep, newsize);
			_eq = Arrays.copyOf(_eq, newsize);
			int os = _elimbrorder.length;
			_elimbrorder = Arrays.copyOf(_elimbrorder, newsize);
			Arrays.fill(_elimbrorder, os, newsize, -1);
			Arrays.fill(_ep, os, newsize, -1);
			Arrays.fill(_eq, os, newsize, -1);
		}
		return newsize;
	}

	/** Called to set up subclass for new elimination run */
	protected abstract void setup(LinkNet matrix);

	/** 
	 * reduce network to the set of ref buses
	 * 
	 * @param matrix Sparse adjacency matrix
	 * @param ref Buses to be retained in the equivalent network.
	 */
	public void eliminate(LinkNet mtrx, int[] ref)
	{
		LinkNet matrix = new LinkNet(mtrx);
		setup(matrix);
		int nmbr = matrix.getBranchCount();
		int cap = nmbr*3;
		matrix.ensureCapacity(0, cap);
		ensureCapacity(cap);
		int[] ccnt = matrix.getConnectionCounts();
		for(int r : ref) ccnt[r] = 0;
		int[] last = new int[(matrix.getMaxBusNdx()+1)/2];
		int nlast = 0;
		BusConnectionsPriQ bcq = new BusConnectionsPriQ(ccnt);
		int elimbus = bcq.peek();
		int[] elimndorder = new int[matrix.getMaxBusNdx()];
		Arrays.fill(elimndorder, -1);
		int brord=0;
		while (elimbus != -1)
		{
			int[][] cinfo = matrix.findConnections(elimbus);
			/** connected buses (to elimbus) */
			int[] cbus = cinfo[0];
			/** connected branches in same order as cbus */
			int[] cbr = cinfo[1];
			/** number of connected buses */
			int nbus = cbus.length;
			/*
			 * allow subclass to start elimination of a bus
			 */
			elimStart(elimbus, cbus, cbr);
			/** keep a counter for each connected bus of adds and removes */
			int[] incc = new int[nbus];
			for (int i = 0; i < nbus; ++i)
			{
				int busi = cbus[i];
				bcq.dec(elimbus);
				--incc[i];
				matrix.eliminateBranch(cbr[i], true);
				_elimbrorder[brord] = cbr[i];
				_ep[brord] = elimbus;
				_eq[brord++] = busi;
				for (int j = i + 1; j < nbus; ++j)
				{
					int busj = cbus[j];
					int br = -1;
					if (ccnt[busi] > 0 || ccnt[busj] > 0)
					{
						br = matrix.findBranch(busi, busj);
						if (br == -1)
						{
							br = matrix.addBranch(busi, busj);
							ensureCapacity(++nmbr);
							++incc[i];
							++incc[j];
						}
					}
					mutual(i, cbr[j], br);
				}
			}
			/* adjust counts */
			for (int i = 0; i < nbus; ++i)
			{
				int n = cbus[i], c = incc[i];
				while (c < 0)
				{
					if (bcq.dec(n)) last[nlast++] = n;
					++c;
				}
				while (c > 0)
				{
					bcq.inc(n);
					--c;
				}
			}
			elimStop();
			elimndorder[_iord++] = elimbus;
			elimbus = bcq.peek();
		}
		_elimndorder = elimndorder;
		finish();
		_elimbrcnt = brord;
	}
	
	/**
	 * Notify subclass that elimination is complete.
	 */
	protected abstract void finish();
	
	/**
	 * Returns a list of how the nodes were eliminated
	 * 
	 * @return array of node indexes in elimination order
	 */
	public int[] getElimNdOrder()
	{
		return _elimndorder;
	}

	/**
	 * Get the number of nodes eliminated
	 * 
	 * @return Number of nodes eliminated
	 */
	public int getElimNdCount()
	{
		return _iord;
	}
	
	/**
	 * Get the number of edges that were eliminated during nodal elimination
	 * @return Number of edges that were eliminated during nodal elimination
	 */
	public int getElimEdgeCount()
	{
		return _elimbrcnt;
	}

	/**
	 * Get the from-side nodes of eliminated edges
	 * @return From-side nodes of eliminated edges
	 */
	public int[] getElimFromNode()
	{
		return _ep;
	}

	/**
	 * Get the to-side nodes of eliminated edges
	 * @return To-side nodes of eliminated edges
	 */
	public int[] getElimToNode()
	{
		return _eq;
	}
	
	/**
	 * Get the order in which edges were eliminated
	 * @return array containing edge indexes in order of elimination
	 */
	public int[] getElimEdgeOrder()
	{
		return _elimbrorder;
	}
}

/**
 * Priority queue of buses ordered by connection counts. Reordering is performed
 * dynamically when counts are changed.
 * 
 * @author chris@powerdata.com
 * 
 */
class BusConnectionsPriQ
{
	static final long	LW	= 0xffffffffL;
	static final long	HW	= 0xffffffff00000000L;
	/** bus connection counts */
	int[]				_conncnt;
	/** sorted index */
	int[]				_sndx;
	/** bus location in sorted index */
	int[]				_rndx;
	/** sublist sizes */
	int[]				_sublist;

	/**
	 * Priority queue of buses ordered by connection counts. Reordering is
	 * performed dynamically when counts are changed.
	 * 
	 * @param counts
	 *            array of connection counts for each bus.
	 */
	public BusConnectionsPriQ(int[] counts)
	{
		_conncnt = counts;
		sort();
		calcSublistSizes();
	}

	protected void calcSublistSizes()
	{
		int n = _conncnt.length;
		_sublist = new int[n + 1];
		int lastcnt = 0;
		for (int i = 0; i < n; ++i)
		{
			int ndx = _sndx[i];
			int c = _conncnt[ndx];
			if (lastcnt < c)
			{
				for (int j = lastcnt; j < c; ++j)
				{
					_sublist[j + 1] = i;
				}
				lastcnt = c;
			}
		}
		int cnt = 0;
		for (int j = _sublist[lastcnt]; j < _sndx.length; ++j)
			++cnt;
		cnt += _sublist[lastcnt];
		for (int j = lastcnt + 1; j < _sublist.length; ++j)
			_sublist[j] = cnt;
	}

	/**
	 * Return a bus with the lowest (but nonzero) number of connections
	 * 
	 * @return Bus with the lowest (but nonzero) number of connections. Return
	 *         -1 if queue is empty (all buses have a 0 count)
	 */
	public int peek()
	{
		int nnd = _conncnt.length;
		for (int i = 2; i < _sublist.length; ++i)
		{
			int im1 = i - 1;
			if (_sublist[im1] == nnd)
				return -1;
			int sim1 = _sublist[im1];
			if (_sublist[i] > sim1)
				return _sndx[sim1];
		}
		return -1;
	}

	protected void sort()
	{
		int nbus = _conncnt.length;
		_sndx = new int[nbus];
		_rndx = new int[nbus];
		long[] ts = new long[nbus];
		for (int i = 0; i < nbus; ++i)
		{
			long w = ((long) _conncnt[i]) << 32;
			ts[i] = w | i;
		}
		Arrays.sort(ts);
		for (int i = 0; i < nbus; ++i)
		{
			int s = (int) (ts[i] & LW);
			_sndx[i] = s;
			_rndx[s] = i;
		}
	}

	/**
	 * Increment the connection count for a bus
	 * 
	 * @param bus
	 *            bus to increment the number of connections
	 */
	public void inc(int bus)
	{
		int ocnt = _conncnt[bus];
		if (ocnt == 0) return;
		int ncnt = ocnt + 1;
		_conncnt[bus] = ncnt;
		int sloc = _rndx[bus];
		int swloc = _sublist[ncnt] - 1;
		if (swloc != sloc)
			swap(sloc, swloc, bus);
		--_sublist[ncnt];
	}

	protected void swap(int sloc, int swloc, int busndx)
	{
		_sndx[sloc] = _sndx[swloc];
		_sndx[swloc] = busndx;
		_rndx[busndx] = swloc;
		_rndx[_sndx[sloc]] = sloc;
	}

	/**
	 * Decrement the connection count for a bus
	 * 
	 * @param bus
	 *            bus to decrement the number of connections
	 * @return true if decrement caused a removal (new count is 0)
	 */
	public boolean dec(int bus)
	{
		int ocnt = _conncnt[bus];
		if (ocnt <= 0)
			return false;
		boolean rv = --_conncnt[bus] == 0;
		/* see if the sorted index needs adjustment */
		int sloc = _rndx[bus];
		/*
		 * if it's the first entry in the sorted sublist for the old count, then
		 * it is immediately adjacent to the new count and no sort is necessary.
		 */
		int swloc = _sublist[ocnt];
		if (swloc != sloc)
			swap(sloc, swloc, bus);
		++_sublist[ocnt];
		return rv;
	}
}