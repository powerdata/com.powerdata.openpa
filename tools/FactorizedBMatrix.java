package com.powerdata.openpa.tools;

public class FactorizedBMatrix
{
	float[] _bself, _bbrofs;
	int[] _pnode, _qnode;
	
	public FactorizedBMatrix(float[] bself, float[] bbrofs, int[] pnode,
			int[] qnode)
	{
		_bself = bself;
		_bbrofs = bbrofs;
		_pnode = pnode;
		_qnode = qnode;
	}
	
	public float[] solve(float[] mismatches)
	{
		float[] mm = mismatches.clone();
		int nnd = _bself.length;
		int nbr = _bbrofs.length;

		/* run the forward reduction */
		for(int i=0; i < nbr; ++i)
		{
			mm[_qnode[i]] += _bbrofs[i] * mm[_pnode[i]];
		}
		
		/* backward substitution */
		float[] dx = new float[nnd];
		for (int i=0; i < nnd; ++i)
		{
			dx[i] = mm[i] / _bself[i];
		}
		for(int i=_bbrofs.length-1; i >= 0; --i)
		{
			dx[_pnode[i]] += _bbrofs[i] *  dx[_qnode[i]];
		}
		
		return dx;
	}
}
