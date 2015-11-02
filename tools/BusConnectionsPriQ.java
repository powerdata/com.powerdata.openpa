package com.powerdata.openpa.tools;

import java.util.Arrays;

/**
 * Priority queue of buses ordered by connection counts. Reordering is performed
 * dynamically when counts are changed.
 * 
 * @author chris@powerdata.com
 * 
 */
public class BusConnectionsPriQ
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
	
	public interface BusFunction
	{
		/**
		 * apply the function.
		 * 
		 * @param bus
		 *            Bus offset
		 * @return true to abort the operation, false to continue
		 */
		boolean apply(int bus);
	}
	
	
	/**
	 * Iterate through each bus in priority order until the end, or else told to stop.
	 * 
	 * @param f
	 *            function to apply on each bus
	 * @return  true if the loop was aborted, false if we iterated over the
	 *         entire collection
	 */
	public boolean forEach(BusFunction f)
	{
		int nnd = _conncnt.length;
		int nsl = _sublist.length;
		for(int i=2; i < nsl; ++i)
		{
			int im1 = i - 1;
			int sim1 = _sublist[im1];
			if (sim1 == nnd)
				return false;
			if(_sublist[i] > sim1)
				if(f.apply(_sndx[sim1])) return true;
		}
		return false;
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

	public int getCount(int bus) {return _conncnt[bus];}
	
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