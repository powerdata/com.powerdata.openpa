package com.powerdata.openpa.tools;

import java.util.Arrays;

import com.powerdata.openpa.tools.SpSymMtrxFactPattern.EliminatedBus;

/**
 * Sparse Symmetric susceptance matrix
 * 
 * @author chris@powerdata.com
 * 
 */
public class SpSymBMatrix
{
	protected class Factorizer extends SpSymMtrxFactorizer
	{
		Factorizer(float[] bdiag, float[] boffdiag)
		{
			bd = bdiag.clone();
			/*
			 * Note that ensureCapacity is called prior to any writes, so
			 * boffdiag does not get modified
			 */
			bo = boffdiag;
		}

		float[]	bd, bo, temp;

		@Override
		protected void elimStart(int elimbus, int[] cbus, int[] cbr)
		{
			int nmut = cbus.length;
			temp = new float[nmut];
			for (int i = 0; i < nmut; ++i)
			{
				float boelim = bo[cbr[i]];
				temp[i] = -boelim / bd[elimbus];
				bd[cbus[i]] += temp[i] * boelim;
			}
		}

		@Override
		protected void mutual(int imut, int adjbr, int targbr)
		{
			bo[targbr] += temp[imut] * bo[adjbr];
		}

		@Override
		protected int ensureCapacity(int newsize)
		{
			newsize = super.ensureCapacity(newsize);
			if (bo.length <= newsize)
				bo = Arrays.copyOf(bo, newsize);
			return newsize;
		}

		public float[] getBDiag()
		{
			return bd;
		}

		public float[] getBOffDiag()
		{
			return bo;
		}

		@Override
		protected void setup(LinkNet matrix)
		{
			// do nothing
		}

		@Override
		protected void finish()
		{
			// do nothing
		}

		@Override
		protected void elimStop()
		{
			// do nothing
		}
	}

	/**
	 * Create a factorized susceptance matrix using a LinkNet sparse symmetric
	 * model representation. Use this constructor to just run factorization a
	 * single time. To save the pattern and run multiple times with different
	 * B-values, use the other constructor.
	 * 
	 * Neither the matrix nor susceptance arrays are modified
	 * 
	 * @param matrix
	 *            LinkNet sparse symmetric network.
	 * @param bdiag
	 *            diagonal susceptance values (array with size equal to number
	 *            of buses)
	 * @param boffdiag
	 *            off-diagonal susceptance values (array with size equal to
	 *            number of branches)
	 * 
	 * @return factorized susceptance matrix
	 * 
	 */
	public FactorizedBMatrix factorize(LinkNet matrix, float[] bdiag,
			float[] boffdiag, int[] save)
	{
		Factorizer f = new Factorizer(bdiag, boffdiag);
		f.eliminate(matrix, save);
		return new FactorizedBMatrix(f.getBDiag(), f.getBOffDiag(),
				f.getElimFromNode(), f.getElimToNode(), f.getElimNdOrder(),
				f.getElimNdCount(), f.getElimBrOrder(), f.getElimBranchCount());
	}

	/**
	 * Create a factorized susceptance matrix using a saved pattern
	 * 
	 * Neither the pattern nor susceptance arrays are modified
	 * 
	 * @param pattern
	 *            Existing factorizer
	 * @param bdiag
	 *            diagonal susceptance values (array with size equal to number
	 *            of buses)
	 * @param boffdiag
	 *            off-diagonal susceptance values (array with size equal to
	 *            number of branches)
	 * 
	 * @return factorized susceptance matrix
	 * 
	 */
	public FactorizedBMatrix factorize(SpSymMtrxFactPattern pattern,
			float[] bdiag, float[] boffdiag)
	{
		Factorizer f = new Factorizer(bdiag, boffdiag);
		f.ensureCapacity(pattern.getElimBranchCount());
		/* fake out the factorize, but use the same equations for B */
		for (EliminatedBus ebusr : pattern.getEliminatedBuses())
		{
			int[] cbus = ebusr.getRemainingNodes();
			int[] cbr = ebusr.getElimBranches();
			f.elimStart(ebusr.getElimBusNdx(), cbus, cbr);
			int[] tbr = ebusr.getRemainingBranches();
			int nmut = cbus.length, imut = 0;
			for (int i = 0; i < nmut; ++i)
			{
				for (int j = i + 1; j < nmut; ++j)
					f.mutual(i, cbr[j], tbr[imut++]);
			}
		}
		return new FactorizedBMatrix(f.getBDiag(), f.getBOffDiag(),
				pattern.getElimFromNode(), pattern.getElimToNode(),
				pattern.getElimNdOrder(), pattern.getElimNdCount(),
				pattern.getElimBrOrder(), pattern.getElimBranchCount());
	}
}
