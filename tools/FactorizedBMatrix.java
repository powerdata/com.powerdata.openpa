package com.powerdata.openpa.tools;

import java.io.PrintWriter;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

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
	boolean[] _buselim;
	
	public FactorizedBMatrix(float[] bself, float[] bbrofs, int[] pnode,
			int[] qnode, int[] brndx, boolean[] buselim)
	{
		_bself = bself;
		_bbrofs = bbrofs;
		_pnode = pnode;
		_qnode = qnode;
		_brndx = brndx;
		_buselim = buselim;
	}

	public void dump(PsseModel model, PrintWriter pw) throws PsseModelException
	{
		pw.println("\"brndx\",\"eord\",\"p\",\"pndx\",\"q\",\"qndx\",\"-bbranch/bself\",\"bself\"");
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

			pw.format("%d,%d,\"%s\",%d,\"%s\",%d,%f,%f\n", _brndx[i], iord,
					buses.get(pn).getNAME(), pn, buses.get(qn).getNAME(), qn,
					_bbrofs[i], _bself[pn]);
		}
	}
	
	/** 
	 * Perform a forward reduction
	 * @param mm mismatch array
	 * @return
	 */
	public float[] forwardReduction(float[] mm)
	{
		int nbus = mm.length;
		int nbr = _bbrofs.length;
		float[] rv = new float[nbus];

		for (int i = 0; i < nbr; ++i)
		{
			rv[_qnode[i]] = mm[_qnode[i]] + _bbrofs[i] * mm[_pnode[i]];
		}
		return rv;
	}

	/**
	 * Perform backward substitution
	 * @param ds result of forward reduction
	 * @return
	 */
	public float[] backwardSubstitution(float[] ds)
	{
		int nbr = _bbrofs.length;
		float[] dx = new float[ds.length];
		for (int i = 0; i < _buselim.length; ++i)
		{
			if (_buselim[i])
				dx[i] = ds[i] / _bself[i];
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
	 * @return
	 */
	public float[] solve(float[] mm)
	{
		return backwardSubstitution(forwardReduction(mm));
	}

	/**
	 * As a convenience, return whether the bus was eliminated or retained during factorization
	 * @param busNdx index of the bus
	 * @return
	 */
	public boolean wasBusEliminated(int busNdx) {return _buselim[busNdx];}
}
