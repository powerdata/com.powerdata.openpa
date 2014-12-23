package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;

public class ACBranchBpElemBldr extends BMtrxElemBldr
{
	class Elem implements BMtrxElem
	{
		float _y;
		
		Elem(float y) {_y = y;}
		
		@Override
		public float getFromSelfB() {return _y;}
		@Override
		public float getToSelfB() {return _y;}
		@Override
		public float getTransferB() {return -_y;}
	}

	protected ACBranchAdjacencies<? extends ACBranchExt> _adj;
	
	public ACBranchBpElemBldr(ACBranchAdjacencies<? extends ACBranchExt> adj)
	{
		super(adj);
		_adj = adj;
	}

	@Override
	public BMtrxElem get(int index)
	{
		float y = 0f;
		for (ACBranchExt brx : _adj.getBranches(index))
		{
			try
			{
				y += 1f/brx.getBranch().getX();
			}
			catch (PAModelException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return new Elem(y);
	}
}
