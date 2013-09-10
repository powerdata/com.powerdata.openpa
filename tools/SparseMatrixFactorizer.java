package com.powerdata.openpa.tools;

import java.util.Arrays;
import java.util.AbstractList;

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
//		LinkNet net = matrix.clone();
		NodeCounts nc = new NodeCounts(net, saveBusNdx);
		int iord = 0, nbus = nc.getNextBusNdx();
		while (nbus != -1)
		{
			_elimorder[iord++] = nbus;
			int[][] cinfo = net.findConnections(nbus);
			int[] nodes = cinfo[0], branches = cinfo[1];
			for(int br : branches) net.eliminateBranch(br, true);
			int nnd = nodes.length;
			_n[nbus] = nodes;
			_bfr[nbus] = branches;
			int itbr = 0;
			int[] tbr = new int[nnd*(nnd-1)/2];
			for(int i=0; i < nnd; ++i)
			{
				for(int j=i+1; j < nnd; ++j)
				{
					int br = net.findBranch(nodes[i], nodes[j]);
					if (br == -1) br = net.addBranch(nodes[i], nodes[j]);
					tbr[itbr++] = br;
				}
			}
			_btr[nbus] = tbr;
			nbus = nc.getNextBusNdx();
		}
		_size = iord;
		_factbrcnt = net.getBranchCount();
		
		
	}
	
	public static void main(String[] args)
	{
		/* test the eliminate */
		LinkNet n = new LinkNet();
		n.ensureCapacity(5, 7);
		n.addBranch(0, 1);
		n.addBranch(1, 2);
		n.addBranch(2, 3);
		n.addBranch(3, 4);
		n.addBranch(1, 4);
		n.addBranch(1, 5);
		n.addBranch(4, 5);
//		n.addBranch(1, 3);
		SparseMatrixFactorizer smf = new SparseMatrixFactorizer(n, new int[]{0});
		System.out.format("Elimination order: %s\n", Arrays.toString(smf._elimorder));
		for(int i=0; i < smf._n.length && smf._elimorder[i] != -1; ++i)
		{
			int nx = smf._elimorder[i];
//			if (smf._n[nx] != null)
//			{
				System.out.format("node %d - n: %s, fbr: %s, tbr: %s\n", nx,
						Arrays.toString(smf._n[nx]), Arrays.toString(smf._bfr[nx]), Arrays.toString(smf._btr[nx]));
//			}
		}
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
	/** the number of connections for each bus index in the linknet */
	int[] _busconncnt;
	/** A list of bus indexes for each connection count */
	int[][] _busbycnt;
	/** starting position for each list of busses */
	int[] _startpos;
	int _lowcount = 1;
	
	public NodeCounts(LinkNet net, int[] saveBusNdx)
	{
		int ndcnt = net.getMaxBusNdx();
		_busconncnt = new int[ndcnt];
		for(int i=0; i < ndcnt; ++i)
		{
			_busconncnt[i] = net.getConnectionCount(i);
		}
		for(int i=0; i < saveBusNdx.length; ++i)
		{
			_busconncnt[saveBusNdx[i]] = -1;
		}
		analyze();
	}
	
	void analyze()
	{
		/* count up all the nodes for each connection "level" */
		int maxconnalloc = 100;
		int maxconnfound = 1;
		int[] buscntbyconn = new int[maxconnalloc];
		for (int i = 0; i < _busconncnt.length; ++i)
		{
			int cnt = _busconncnt[i];
			if (cnt > 0)
			{
				if (cnt > maxconnfound)
					maxconnfound = cnt;
				if (cnt >= maxconnalloc)
				{
					maxconnalloc *= 2;
					buscntbyconn = Arrays.copyOf(buscntbyconn, maxconnalloc);
				}
				++buscntbyconn[_busconncnt[i]];
			}
		}
		
		/* build the list of bus indexes, in order of connection count */
		_busbycnt = new int[maxconnfound+1][];
		int[] cpos = new int[maxconnfound+1];
		for(int i=1; i <= maxconnfound; ++i)
		{
			_busbycnt[i] = new int[buscntbyconn[i]];
		}
		for(int i=0; i < _busconncnt.length; ++i)
		{
			int cnt = _busconncnt[i];
			if (cnt > 0) _busbycnt[cnt][cpos[cnt]++] = i;
		}
		_startpos = new int[maxconnfound+1];
	}
	
	public int getNextBusNdx()
	{
		int cnt = _lowcount;
		while (cnt < _busbycnt.length)
		{
			int[] bbc = _busbycnt[cnt];
			int pos = _startpos[cnt];
			while (pos < bbc.length)
			{
				int busndx = bbc[pos++];
				// TODO: See how bad the count increases really get
//				int acnt = _busconncnt[busndx];
//				if ((acnt - cnt) < 1)
//				{
					++_startpos[cnt];
					_busconncnt[busndx] = -1;
					return busndx;
//				}
			}
			_lowcount = ++cnt;
		}
		return -1;
	}
	
}