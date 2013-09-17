package com.powerdata.openpa.tools;

import java.io.PrintWriter;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class FactorizedBMatrix
{
	float[] _bself, _bbrofs;
	int[] _pnode, _qnode, _brndx;
	
	public FactorizedBMatrix(float[] bself, float[] bbrofs, int[] pnode,
			int[] qnode, int[] brndx)
	{
		_bself = bself;
		_bbrofs = bbrofs;
		_pnode = pnode;
		_qnode = qnode;
		_brndx = brndx;
	}
	
	public void dump(PsseModel model, PrintWriter pw) throws PsseModelException
	{
		pw.println("\"brndx\",\"eord\",\"p\",\"pndx\",\"q\",\"qndx\",\"-bbranch/bself\",\"bself\"");
		BusList buses = model.getBuses();
		int iord=-1;
		int oldpn = -1;
		for(int i=0; i < _pnode.length; ++i)
		{
			int pn = _pnode[i];
			int qn = _qnode[i];
			if (pn != oldpn)
			{
				oldpn = pn;
				++iord;
			}
			
			pw.format("%d,%d,\"%s\",%d,\"%s\",%d,%f,%f\n", _brndx[i], iord, buses.get(pn).getNAME(),
					pn, buses.get(qn).getNAME(), qn, _bbrofs[i], _bself[pn]);
		}
	}
	
	public float[] solve(float[] mm, float[] vm)
	{
		int nnd = _bself.length;
		int nbr = _bbrofs.length;

		for (int i=0; i < nnd; ++i)
		{
			mm[i] /= vm[i];
		}
		
		/* run the forward reduction */
		for(int i=0; i < nbr; ++i)
		{
			mm[_qnode[i]] += _bbrofs[i] * mm[_pnode[i]];
		}
		
		/* backward substitution */
		float[] dx = new float[nnd];
//		for(int[] buslist : buses)
//		{
//			for (int b : buslist)
//			{
//				dx[b] = mm[b] / _bself[b];
//			}
//		}
		for(int i=nbr-1; i >= 0; --i)
		{
			int pn = _pnode[i];
			dx[pn] = mm[pn] / _bself[pn];
			dx[pn] += _bbrofs[i] *  dx[_qnode[i]];
		}
		
		return dx;
	}
}
