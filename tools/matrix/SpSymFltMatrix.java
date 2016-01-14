package com.powerdata.openpa.tools.matrix;

import java.io.PrintWriter;
import java.util.Arrays;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.matrix.SpSymMtrxFactPattern.EliminatedNode;

/**
 * Sparse Symmetric matrix with a floating-point value.
 * 
 * This class effectively "wraps" a set of adjacencies (LinkNet) created independently. It
 * is intended to allow the same set of adjacencies to be used in multiple
 * matrices...as such no changes are permitted to adjacencies after this object
 * is constructed and initialized.  
 * 
 * 
 * @author chris@powerdata.com
 * 
 */
public class SpSymFltMatrix implements FloatMatrix
{
	/**
	 * Inner Factorizer class to perform factorization against floating-point value
	 * @author chris@powerdata.com
	 *
	 */
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
			if (targbr != -1) 
			{
				bo[targbr] += temp[imut] * bo[adjbr];
			}
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

	protected LinkNet _adj;
	protected float[] _bdiag, _boffdiag;
	

	/**
	 * Expect that the sub class has enough information to
	 */
	protected SpSymFltMatrix() {}
	protected void setAdjacencies(LinkNet adj) {_adj = adj;}
	protected void setBDiag(float[] bdiag) {_bdiag = bdiag;}
	protected void setBOffDiag(float[] boffdiag) {_boffdiag = boffdiag;}

	public FactorizedFltMatrix factorize(int[] ref)
	{
		Factorizer f = new Factorizer(_bdiag, _boffdiag);
		f.eliminate(_adj, ref);
		return new FactorizedFltMatrix(f.getBDiag(), f.getBOffDiag(),
				f.getElimFromNode(), f.getElimToNode(), f.getElimNdOrder(),
				f.getElimNdCount(), f.getElimEdgeOrder(), f.getElimEdgeCount());
	}
	
	@Override
	public void addValue(int f, int t, float b)
	{
		if (f == t)
			_bdiag[f] += b;
		else
			_boffdiag[_adj.findBranch(f, t)] += b;
	}
	
	/** modify susceptence entry on the diagonal */
	public void incBdiag(int bus, float b)
	{
		_bdiag[bus] += b;
	}
	
	/** modify susceptance for an off-diagonal entry */
	public void incBoffdiag(int br, float b)
	{
		_boffdiag[br] += b;
	}

	/**
	 * Create a factorized susceptance matrix using a saved pattern
	 * 
	 * Neither the pattern nor susceptance arrays are modified
	 * 
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
	public FactorizedFltMatrix factorize(SpSymMtrxFactPattern pat)
	{
		Factorizer f = new Factorizer(_bdiag, _boffdiag);
		f.ensureCapacity(pat.getElimEdgeCount());
		/* fake out the factorize, but use the same equations for B */
		for (EliminatedNode ebusr : pat.getEliminatedBuses())
		{
			int[] cbus = ebusr.getRemainingNodes();
			int[] cbr = ebusr.getElimEdges();
			f.elimStart(ebusr.getElimNodeNdx(), cbus, cbr);
			int[] tbr = ebusr.getFilledInEdges();
			int nmut = cbus.length, imut = 0;
			for (int i = 0; i < nmut; ++i)
			{
				for (int j = i + 1; j < nmut; ++j)
					f.mutual(i, cbr[j], tbr[imut++]);
			}
		}
		return new FactorizedFltMatrix(f.getBDiag(), f.getBOffDiag(),
				pat.getElimFromNode(), pat.getElimToNode(),
				pat.getElimNdOrder(), pat.getElimNdCount(),
				pat.getElimEdgeOrder(), pat.getElimEdgeCount());
	}

	public void dump(String[] name, PrintWriter pw)
	{
		pw.println("BranchNdx,Btran,FromNdx,FromName,FromBself,ToNdx,ToName,ToBself");
		int nbr = _adj.getBranchCount();
		for(int i=0; i < nbr; ++i)
		{
			int[] nd = _adj.getBusesForBranch(i);
			int f = nd[0], t = nd[1];
			pw.format("%d,%f,%d,'%s',%f,%d,'%s',%f\n",
				i, _boffdiag[i],
				f, name[f], _bdiag[f],
				t, name[t], _bdiag[t]);
				
		}
		
	}

	public float[] getBDiag()
	{
		return _bdiag;
	}
	@Override
	public int getRowCount()
	{
		return _bdiag.length;
	}
	@Override
	public int getColumnCount()
	{
		return _bdiag.length;
	}
	@Override
	public void setValue(int row, int column, float value)
	{
		if (row == column)
			_bdiag[row] = value;
		else
			_boffdiag[_adj.findBranch(row, column)] = value;
	}
	@Override
	public float getValue(int row, int column)
	{
		if (row == column)
			return _bdiag[row];
		else
			return _boffdiag[_adj.findBranch(row, column)];
	}
	@Override
	public void multValue(int row, int column, float value)
	{
		if (row == column)
			_bdiag[row] *= value;
		else
			_boffdiag[_adj.findBranch(row, column)] *= value;
	}
}
