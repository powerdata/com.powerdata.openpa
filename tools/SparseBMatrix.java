package com.powerdata.openpa.tools;

import java.util.Arrays;

import com.powerdata.openpa.tools.SparseMatrixFactorizer.EliminatedBus;

/**
 * Manage a sparse B matrix.  
 * 
 * TODO:  allow the original bself and bbranch values to be changed, so rhat 
 * factorization can be re-run without creating a new object (and thus requiring elimination 
 * to be re-run)
 * 
 * @author chris@powerdata.com
 *
 */
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
		int nfactbr = (int) (_factorizer.getFactorizedBranchCount() * 1.25f);
		_pnode = new int[nfactbr];
		_qnode = new int[nfactbr];
		_elimbrndx = new int[nfactbr];
		int nused=0;
		for(int ielim=0, jbr=0; ielim < _factorizer.size(); ++ielim)
		{
			EliminatedBus ebus = _factorizer.get(ielim);
			int[] cnodes = ebus.getRemainingNodes();
			int[] elimbr = ebus.getElimBranches();
			int ebusndx = ebus.getElimBusNdx();
			for(int i=0; i < cnodes.length; ++i, ++jbr)
			{
				++nused;
				_pnode[jbr] = ebusndx;
				_qnode[jbr] = cnodes[i];
				_elimbrndx[jbr] = elimbr[i];
			}
		}
		if (nused < nfactbr)
		{
			_pnode = Arrays.copyOf(_pnode, nused);
			_qnode = Arrays.copyOf(_qnode, nused);
			_elimbrndx = Arrays.copyOf(_elimbrndx, nused);
		}
	}
	
	/** factorize the B matrix */
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
			int itbr=0;
			for (int i=0; i < cnodes.length; ++i)
			{
				float bbrdiag = bbranch[cbr[i]];
				float bprep = -bbrdiag / bself[ebus.getElimBusNdx()];
				bself[cnodes[i]] += bprep * bbrdiag;
				for (int j=i+1; j < cnodes.length; ++j, ++itbr)
				{
//					int bbrndx = tbr[itbr];
//					if (bbrndx != -1)
//						bbranch[bbrndx] += bprep * bbranch[cbr[j]];
					bbranch[tbr[itbr]] += bprep * bbranch[cbr[j]];
				}
			}
		}

		/* convert bbranch to eliminated order */
		int nwl = _pnode.length;
		float[] elimbbr = new float[nwl];
		for(int i=0; i < nwl; ++i)
		{
			elimbbr[i] = -bbranch[_elimbrndx[i]] / bself[_pnode[i]];
		}
		return new FactorizedBMatrix(bself, elimbbr, _pnode, _qnode, _elimbrndx);
	}

}
