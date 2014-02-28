package com.powerdata.openpa.tools;

import java.io.PrintWriter;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;

/**
 * Keep a more efficient version of the factorized sparse matrix.  
 * 
 * @author chris@powerdata.com
 *
 */

public class FactorizedBMatrix
{
	float[] _bself, _bbrofs;
	int[] _pnode, _qnode, _brndx;
	/** bus eliminated */
	int[] _buselim;
	
	FactorizedBMatrix(float[] bself, float[] bbrofs, int[] pnode,
			int[] qnode, int[] brndx, int[] buselim)
	{
		_bself = bself;
		_bbrofs = bbrofs;
		_pnode = pnode;
		_qnode = qnode;
		_brndx = brndx;
		_buselim = buselim;
	}

	public void dump(BusGroup2TDevList tn, PrintWriter pw) throws PsseModelException
	{
		pw.println("'brndx','eord','p','pndx','q','qndx','-bbranch/bself','bself'");
		BusList buses = model.getBuses();
		int iord = -1;
		int oldpn = -1;
		for (int i = 0; i < _pnode.length; ++i)
		{
			int pn = _pnode[i];
			int qn = _qnode[i];
			if (pn != oldpn)
			{
				oldpn = pn;
				++iord;
			}

			pw.format("%d,%d,'%s',%d,'%s',%d,%f,%f\n", _brndx[i], iord,
					buses.get(pn).getNAME(), pn, buses.get(qn).getNAME(), qn,
					_bbrofs[i], _bself[pn]);
		}
	}
	
	/** 
	 * Perform a forward reduction
	 * @param mm mismatch array
	 * @return Array (in bus order) results of forward reduction results 
	 */
	public float[] forwardReduction(float[] mm)
	{
		int nbr = _bbrofs.length;
		float[] rv = mm.clone();

		for (int i = 0; i < nbr; ++i)
		{
			rv[_qnode[i]] += _bbrofs[i] * rv[_pnode[i]];
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
		int nbr = _bbrofs.length;
		float[] dx = new float[ds.length];
		for(int bus : _buselim)
		{
			dx[bus] = ds[bus] / _bself[bus];
		}
		for (int i = nbr - 1; i >= 0; --i)
		{
			dx[_pnode[i]] += _bbrofs[i] * dx[_qnode[i]];
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

	/**
	 * As a convenience, return buses that we determined get eliminated
	 * @return array of eliminated bus indexes
	 */
	public int[] getEliminatedBuses() {return _buselim;}
}
