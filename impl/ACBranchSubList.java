package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
