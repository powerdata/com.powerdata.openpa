package com.powerdata.openpa.tools.matrix;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Keep a more efficient version of the float-valued factorized sparse matrix.  
 * 
 * @author chris@powerdata.com
 *
 */

public class FactorizedFltMatrix
{
	float[] _bd, _adjbo;
	int[] _p, _q, _elimbusord;
	public FactorizedFltMatrix(float[] bDiag, float[] bOffDiag,
			int[] p, int[] q, int[] elimNdOrder,int elimBusCnt,
			int[] elimBrOrder, int elimBranchCount)
	{
		_bd = bDiag.clone();
		_p = Arrays.copyOf(p, elimBranchCount);
		_q = Arrays.copyOf(q, elimBranchCount);
		_adjbo = new float[elimBranchCount];
		for(int i=0; i < elimBranchCount; ++i)
			_adjbo[i] = -bOffDiag[elimBrOrder[i]] / bDiag[p[i]];
		_elimbusord = Arrays.copyOf(elimNdOrder, elimBusCnt);
	}

	/** 
	 * Perform a forward reduction
	 * @param mm mismatch array
	 * @return Array (in bus order) results of forward reduction results 
	 */
	public float[] forwardReduction(float[] mm)
	{
		int nbr = _adjbo.length;
		float[] rv = mm.clone();

		for (int i = 0; i < nbr; ++i)
		{
			rv[_q[i]] += _adjbo[i] * rv[_p[i]];
		}
		return rv;
	}

	/**
	 * Perform backward substitution
	 * @param ds result of forward reduction
	 * @return Array (in bus order) of corrections
	 */
	public float[] backwardSubstitution(float[] ds)
	{
		int nbr = _adjbo.length;
		float[] dx = new float[ds.length];
		for(int bus : _elimbusord)
		{
			dx[bus] = ds[bus] / _bd[bus];
		}
		for (int i = nbr - 1; i >= 0; --i)
		{
			dx[_p[i]] += _adjbo[i] * dx[_q[i]];
		}
		return dx;
	}
	
	/**
	 * convenience method to solve the matrix by running both forward reduction
	 * and backward substitution
	 * 
	 * @param mm
	 *            Mismatch array
	 * @return Array (in bus order) of corrections
	 */
	public float[] solve(float[] mm)
	{
		return backwardSubstitution(forwardReduction(mm));
	}

	public int[] getElimBus()
	{
		return _elimbusord;
	}
	
	public void dump(String[] busname, PrintWriter pw)
	{
		pw.println("ElimBus,EBNdx,Bself1,ElimFarBus,FBNdx,Bself2,Btrans/Bself");
		int n = _p.length;
		for(int i=0; i < n; ++i)
		{
			int p = _p[i], q = _q[i];
			pw.format("'%s',%d,%f,'%s',%d,%f,%f\n",
				busname[p], p, _bd[p], busname[q],q, _bd[q], _adjbo[i]);
		}
	}

}
