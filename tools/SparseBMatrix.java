package com.powerdata.openpa.tools;

import java.util.Arrays;

import com.powerdata.openpa.tools.SparseMatrixFactorizer.EliminatedBus;

public class SparseBMatrix
{
	SparseMatrixFactorizer _factorizer;
	float[] _bbranch, _bself;
	int[] _pnode, _qnode, _elimbrndx;
	
	public SparseBMatrix(LinkNet net, int[] saveBusNdx, float[] bbranch, float[] bself)
	{
		_factorizer = new SparseMatrixFactorizer(net, saveBusNdx);
		_bbranch = bbranch;
		_bself = bself;
		_pnode = new int[_factorizer.getFactorizedBranchCount()];
		_qnode = new int[_factorizer.getFactorizedBranchCount()];
		_elimbrndx = new int[_factorizer.getFactorizedBranchCount()];
		for(int ielim=0, jbr=0; ielim < _factorizer.size(); ++ielim)
		{
			EliminatedBus ebus = _factorizer.get(ielim);
			int[] cnodes = ebus.getRemainingNodes();
			int[] elimbr = ebus.getElimBranches();
			int ebusndx = ebus.getElimBusNdx();
			for(int i=0; i < cnodes.length; ++i, ++jbr)
			{
//				for(int j=1; j < cnodes.length; ++j, ++jbr)
//				{
					_pnode[jbr] = ebusndx;
					_qnode[jbr] = cnodes[i];
					_elimbrndx[jbr] = elimbr[i];
//				}
			}
		}
	}
	
	public FactorizedBMatrix factorize()
	{
		float[] bself = _bself.clone();
		int nfbr = _factorizer.getFactorizedBranchCount();
		float[] bbranch = Arrays.copyOf(_bbranch, nfbr);
		int nelim = _factorizer.size();
		for(int ielim=0; ielim < nelim; ++ielim)
		{
			EliminatedBus ebus = _factorizer.get(ielim);
			int[] cnodes = ebus.getRemainingNodes();
			int[] cbr = ebus.getElimBranches();
			int[] tbr = ebus.getRemainingBranches();
			for (int i=0; i < cnodes.length; ++i)
			{
				float bbrdiag = bbranch[cbr[i]];
				if (Math.abs(bself[ebus.getElimBusNdx()]) < .01)
				{
					int xxx = 5;
				}
				float bprep = -bbrdiag / bself[ebus.getElimBusNdx()];
				bself[cnodes[i]] += bprep * bbrdiag;
				int itarg = 0;
				for(int j=1; j < cnodes.length; ++j)
				{
					int bbrndx = tbr[itarg++];
					if (bbrndx != -1)
						bbranch[bbrndx] += bprep * bbranch[cbr[j]];
				}
			}
		}

		/* convert bbranch to eliminated order */
		float[] elimbbr = new float[nfbr];
		for(int i=0; i < nfbr; ++i)
		{
			elimbbr[i] = -bbranch[_elimbrndx[i]] / bself[_pnode[i]];
		}
		return new FactorizedBMatrix(bself, elimbbr, _pnode, _qnode);
	}

}
