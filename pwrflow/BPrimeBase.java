package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.pwrflow.BMtrxElemBldr.BMtrxElem;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.SpSymFltMatrix;

public class BPrimeBase extends SpSymFltMatrix
{
	public <T extends ACBranchExt> BPrimeBase(ACBranchAdjacencies<T> adj, BMtrxElemBldr bldr)
	{
		super(adj);
		int nbr = adj.getBranchCount();
		float[] boffdiag = new float[nbr];
		float[] bdiag = new float[adj.getMaxBusNdx()];
		for(int i=0; i < nbr; ++i)
		{
			BMtrxElem elem = bldr.get(i);
			int[] bus = adj.getBusesForBranch(i);
			if(bus[0] != bus[1])
			{
				boffdiag[i] = elem.getTransferB();
				bdiag[bus[0]] += elem.getFromSelfB();
				bdiag[bus[1]] += elem.getToSelfB();
			}
		}
		setBDiag(bdiag);
		setBOffDiag(boffdiag);
	}
}
