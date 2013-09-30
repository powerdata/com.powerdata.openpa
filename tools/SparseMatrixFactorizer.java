package com.powerdata.openpa.tools;

import java.util.Arrays;
import java.util.AbstractList;

/**
 * Perform elimination and keep the the order stored to allow for changes to the
 * matrix outside of topology
 * 
 * @author chris@powerdata.com
 * 
 */

public class SparseMatrixFactorizer
		extends
		AbstractList<com.powerdata.openpa.tools.SparseMatrixFactorizer.EliminatedBus>
{
	public class EliminatedBus
	{
		int _ndx;
		EliminatedBus(int ndx)
		{
			_ndx = ndx;
		}
		
		public int getOrder() {return _ndx;}
		public int getElimBusNdx() {return _elimorder[_ndx];}
		public int[] getRemainingNodes() {return _n[_ndx];}
		public int[] getElimBranches() {return _bfr[_ndx];}
		public int[] getRemainingBranches() {return _btr[_ndx];}

		@Override
		public String toString()
		{
			return String.format("Bus: index=%d, elimpos=%d", _elimorder[_ndx], _ndx);
		}
		
	}

	int[][] _n;
	int[][] _bfr,_btr;
	int[] _elimorder;
	int[] _pnode, _qnode;
	int _size;
	int _factbrcnt = 0;
	
	public SparseMatrixFactorizer(LinkNet matrix, int[] saveBusNdx)
	{
		int nnd = matrix.getMaxBusNdx();
		_n = new int[nnd][];
		_btr = new int[nnd][];
		_bfr = new int[nnd][];
		_elimorder = new int[nnd]; Arrays.fill(_elimorder, -1);
		
		eliminate(matrix, saveBusNdx);
	}

	void eliminate(LinkNet net, int[] saveBusNdx)
	{
		NodeCounts nc = new NodeCounts(net, saveBusNdx);
		
		int iord = 0, nbus = nc.getNextBusNdx();
		while (nbus != -1)
		{
			_elimorder[iord] = nbus;
			int[][] cinfo = net.findConnections(nbus);
			int[] nodes = cinfo[0], branches = cinfo[1];
			int nnd = nodes.length;
			_n[iord] = nodes;
			_bfr[iord] = branches;
			int itbr = 0;
			int[] tbr = new int[nnd*(nnd-1)/2];
			Arrays.fill(tbr, -1);
			for(int i=0; i < nnd; ++i)
			{
				for(int j=i+1; j < nnd; ++j)
				{
					int br = -1;
					if (!nc.isSaved(nodes[i]) && !nc.isSaved(nodes[j]))
					{
						br = net.findBranch(nodes[i], nodes[j]);
						if (br == -1)
						{
							br = net.addBranch(nodes[i], nodes[j]);
							nc.inc(nodes[i]);
							nc.inc(nodes[j]);
						}
					}
					tbr[itbr++] = br;
				}
			}
			_btr[iord] = tbr;
			for(int i=0; i < branches.length; ++i)
			{
				net.eliminateBranch(branches[i], true);
				nc.dec(nodes[i]);
			}
			nbus = nc.getNextBusNdx();
			++iord;
		}
		
		_size = iord;
		_factbrcnt = net.getBranchCount();
		
	}
	
	public int[] getEliminationOrder() {return _elimorder;}
	@Override
	public int size() {return _size;}
	@Override
	public EliminatedBus get(int ndx) {return new EliminatedBus(ndx);}
	
	public int getFactorizedBranchCount() {return _factbrcnt;}
}

class NodeCounts
{
	static final long LW = 0xffffffffL;
	static final long HW = 0xffffffff00000000L;
	
//	/** bus connection counts */
	int[] _conncnt;
	/** count distribution */
	int[] _cntdist;
//	/** index sorted by count */
	int[] _sndx;
	/** next nonzero count */
	int _nz = 0;
	
	public NodeCounts(LinkNet net, int[] saveBusNdx)
	{
//		eord = isp ? elimorderp : elimorderq;
		analyze(net, saveBusNdx);
		sort();
	}

	public boolean isSaved(int i)
	{
		return _conncnt[i] == 0;
	}
	
	

	public int getNextBusNdx()
	{
		int rv = -1;
		for(int i=1; i < _cntdist.length; ++i)
		{
			if (_cntdist[i] > 0)
			{
				rv = findBus(i);
				--_cntdist[i];
				_conncnt[rv] = 0;
				break;
			}
		}
		return rv;
	}

//	static final int[]	elimorderp	= new int[] { 4, 8, 17, 18, 22, 35, 43, 64,
//			1, 2, 9, 11, 12, 15, 16, 19, 21, 23, 20, 34, 37, 38, 41, 40, 47,
//			49, 50, 52, 53, 54, 55, 56, 58, 59, 60, 62, 63, 65, 66, 67, 68, 69,
//			0, 5, 6, 3, 7, 10, 13, 14, 25, 29, 31, 32, 39, 24, 26, 27, 36, 46,
//			51, 61, 33, 42, 44, 45, 48, 57, 28 };
//	
//	static final int[]	elimorderq	= new int[] { 64, 1, 2, 9, 11, 12, 15, 19,
//			23, 20, 34, 37, 38, 41, 40, 47, 49, 50, 52, 53, 54, 55, 56, 58, 59,
//			60, 62, 63, 65, 66, 67, 68, 69, 0, 5, 6, 10, 13, 14, 29, 31, 32,
//			36, 39, 3, 16, 21, 46, 61, 45, 51, 28, 7, 33, 44, 57, 48, 42 };
//	
//	int _nb = 0;
//	int[] eord = elimorderp;

//	public int getNextBusNdx()
//	{
//		int rv = -1;
//		if (_nb < eord.length)
//		{
//			rv = eord[_nb++];
//			int cnt = _conncnt[rv];
//			--_cntdist[cnt];
//			_conncnt[rv] = 0;
//		}
//		return rv;
//	}

	int findBus(int cd)
	{
		for(int i=_nz; i < _sndx.length; ++i)
		{
			int bndx = getLW(_sndx[i]);
			int cnt = _conncnt[bndx];
			if (cnt == cd)
			{
				return bndx;
			}
//			else if (cnt == 0)
//			{
//				++_nz;
//			}
		}
		/* TODO:  should never get here, put a debug message in until tested */
		throw new UnsupportedOperationException("Should never get here");
//		return -1;
	}

	int getHW(long l)
	{
		return (int) (l & HW);
	}

	int getLW(long l)
	{
		return (int) (l & LW);
	}

	void sort()
	{
		int nbus = _conncnt.length;
		_sndx = new int[nbus];

		long[] ts = new long[nbus];
		for(int i=0; i < nbus; ++i)
		{
			long w = ((long) _conncnt[i]) << 32;
			w |= i;
			ts[i] = w;
		}
		
		Arrays.sort(ts);
		for(int i=0; i < nbus; ++i)
		{
			_sndx[i] = (int) (ts[i] & LW);
		}
		
		
		for(int sx : _sndx)
		{
			if (_conncnt[sx] <= 0)
			{
				++_nz;
			}
			else
			{
				break;
			}
		}
	}

	void analyze(LinkNet net, int[] saveBusNdx)
	{
		/* count up all the nodes for each connection "level" */
		int maxconnalloc = 100;
		int maxconnfound = 1;
		int[] buscntbyconn = new int[maxconnalloc];
		int nbus = net.getMaxBusNdx();
		_conncnt = new int[nbus];
		for (int i = 0; i < nbus; ++i)
		{
			int cnt = net.getConnectionCount(i);
			_conncnt[i] = cnt;
		}
		for(int b : saveBusNdx) _conncnt[b] = 0;
		
		for (int i = 0; i < nbus; ++i)
		{
			int cnt = _conncnt[i];
			if (cnt > 0)
			{
				if (cnt > maxconnfound)
					maxconnfound = cnt;
				if (cnt >= maxconnalloc)
				{
					maxconnalloc *= 2;
					buscntbyconn = Arrays.copyOf(buscntbyconn, maxconnalloc);
				}
				++buscntbyconn[cnt];
			}
		}
		_cntdist = Arrays.copyOf(buscntbyconn, maxconnfound+1);

	}

	public void inc(int busndx)
	{
		int ccnt = _conncnt[busndx];
		if ((ccnt+1) >= _cntdist.length)
			_cntdist = Arrays.copyOf(_cntdist, _cntdist.length*2);
		--_cntdist[ccnt];
		++_cntdist[++ccnt];
		_conncnt[busndx] = ccnt;
	}
	
	public void dec(int busndx)
	{
		int ccnt = _conncnt[busndx];
		if (ccnt > 0)
		{
			--_cntdist[ccnt];
			++_cntdist[--ccnt];
			_conncnt[busndx] = ccnt;
		}
	}
	
}

