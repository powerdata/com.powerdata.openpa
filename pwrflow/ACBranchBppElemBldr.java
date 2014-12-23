package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;

public class ACBranchBppElemBldr extends BMtrxElemBldr
{
	static class Elem implements BMtrxElem
	{
		float _bsf, _bst, _btr;
		Elem(float bsf, float bst, float btr)
		{
			_bsf = bsf;
			_bst = bst;
			_btr = btr;
		}
		@Override
		public float getFromSelfB() {return _bsf;}
		@Override
		public float getToSelfB() {return _bst;}
		@Override
		public float getTransferB() {return _btr;}
	}
	
	protected ACBranchAdjacencies<? extends ACBranchExt> _adj;
	
	public ACBranchBppElemBldr(ACBranchAdjacencies<? extends ACBranchExt> adj)
	{
		super(adj);
		_adj = adj;
	}

	@Override
	public BMtrxElem get(int index)
	{
		float bsf = 0f, bst = 0f, btr=0f;
		for (ACBranchExt brx : _adj.getBranches(index))
		{
			ACBranch br = brx.getBranch();
			try
			{
				float a = br.getFromTap(), b = br.getToTap(), bm = br.getBmag(), 
						y = -brx.getY().im();
				bsf += y/(b*b) - bm - br.getFromBchg();
				bst += y/(a*a) - bm - br.getToBchg();
				btr -= y/(a*b);
			}
			catch (PAModelException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return new Elem(bsf, bst, btr);
	}

}
