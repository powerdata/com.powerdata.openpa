package com.powerdata.openpa.tools;

import java.util.Arrays;
import java.util.AbstractList;

import com.powerdata.tools.utils.QuickSort;
import com.powerdata.tools.utils.SmoothSort;

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
		
		/* count connections between saved buses */
		int nmutsaved = 0;
		for (int sb : saveBusNdx)
		{
			int[] c = net.findBuses(sb);
			for (int cb : c)
			{
				if (nc.isSaved(cb))
					++nmutsaved;
			}
		}
		
		nmutsaved /= 2;
		
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
		_factbrcnt = net.getBranchCount()-nmutsaved;
		
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
	/** bus connection counts */
	int[] _conncnt;
	/** count distribution */
	int[] _cntdist;
	/** index sorted by count */
	int[] _sndx;
	/** next nonzero count */
	int _nz = 0;
	
	public NodeCounts(LinkNet net, int[] saveBusNdx)
	{
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

//	static final int[] elimorder = new int[]
//	{
//		4,8,17,18,22,30,35,64,1,2,5,9,11,12,
//		15,16,19,21,23,20,31,34,37,38,40,47,
//		49,50,52,53,54,55,56,58,59,60,62,63,
//		65,66,67,68,69,0,3,7,10,13,14,25,29,
//		39,24,26,27,36,46,51,61,33,28,44,45,
//		48,57,42
//	};
//	int _nb = 0;
//	public int getNextBusNdx()
//	{
//		if (_nb < elimorder.length)
//		{
//			int bx = elimorder[_nb++];
//			return _conncnt[bx] <= 0 ? -1 : bx;
//		}
//		return -1;
//	}

	int findBus(int cd)
	{
		for(int i=_nz; i < _sndx.length; ++i)
		{
			int bndx = _sndx[i];
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

	void sort()
	{
		final int nbus = _conncnt.length;
		_sndx = new int[nbus];
		for(int i=0; i < nbus; ++i) _sndx[i] = i;
//		new SmoothSort()
//		{
//			int v;
//			@Override
//			public void storeVal(int from)
//			{
//				v = _sndx[from];
//			}
//
//			@Override
//			public void setVal(int to)
//			{
//				_sndx[to] = v;
//			}
//
//			@Override
//			public int compareVal(int ofs)
//			{
//				return _conncnt[_sndx[v]] - _conncnt[_sndx[ofs]];
//			}
//
//			@Override
//			public int compare(int ofs1, int ofs2)
//			{
//				return _conncnt[_sndx[ofs1]] - _conncnt[_sndx[ofs2]];
//			}
//
//			@Override
//			public void set(int to, int from)
//			{
//				_sndx[to] = _sndx[from];
//			}
//
//			@Override
//			public int length()
//			{
//				return nbus;
//			}
//		}.sort();
			
		new QuickSort()
		{
			@Override
			protected boolean isLess(int offset1, int offset2)
			{
				return _conncnt[_sndx[offset1]] < _conncnt[_sndx[offset2]];
			}

			@Override
			protected void swap(int offset1, int offset2)
			{
				int t = _sndx[offset1];
				_sndx[offset1] = _sndx[offset2];
				_sndx[offset2] = t;
			}

			@Override
			protected int size()
			{
				return nbus;
			}
		}.sort();
		
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

