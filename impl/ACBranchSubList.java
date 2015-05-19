package com.powerdata.openpa.impl;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;

public class ACBranchSubList extends ACBranchSubListBase<ACBranch> implements
		ACBranchListIfc<ACBranch>
{
	public ACBranchSubList(ACBranchListIfc<ACBranch> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}


	@Override
	public ACBranch get(int index)
	{
		return new ACBranch(this, index);
	}
}
