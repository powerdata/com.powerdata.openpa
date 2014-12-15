package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;

public class BPrime<T extends ACBranchExt> extends BPrimeBase
{
	public BPrime(ACBranchAdjacencies<T> adj)
	{
		super(adj, new ACBranchBpElemBldr(adj));
	}
}
