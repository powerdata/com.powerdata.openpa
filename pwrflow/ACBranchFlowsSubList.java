package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchFlows.ACBranchFlow;

public class ACBranchFlowsSubList extends ACBranchExtSublist<ACBranchFlow>
		implements ACBranchFlows
{
	ACBranchFlows _src;

	public ACBranchFlowsSubList(ACBranchFlows src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getFromPpu(int ndx)
	{
		return _src.getFromPpu(_ndx[ndx]);
	}

	@Override
	public float getToQpu(int ndx)
	{
		return _src.getToQpu(_ndx[ndx]);
	}

	@Override
	public float getToPpu(int ndx)
	{
		return _src.getToPpu(_ndx[ndx]);
	}

	@Override
	public float getFromQpu(int ndx)
	{
		return _src.getFromQpu(_ndx[ndx]);
	}

	@Override
	public void calc(float[] vmpu, float[] varad) throws PAModelException
	{
		_src.calc(vmpu, varad);
	}

	@Override
	public void applyMismatches(Mismatch pmm, Mismatch qmm) throws PAModelException
	{
//		int n = size();
//
//		for(int i=0; i < n; ++i)
//		{
//			int fbx = getFromBus(i).getIndex();
//			int tbx = getToBus(i).getIndex();
//			pmm.add(fbx, getFromPpu(i));
//			pmm.add(tbx,getToPpu(i));
//			qmm.add(fbx, getFromQpu(i));
//			qmm.add(tbx, getToQpu(i));
//		}
		
		_src.applyMismatches(pmm, qmm, _ndx);
	}

	@Override
	public void update() throws PAModelException
	{
		for (int n : _ndx) _src.update(n);
	}

	@Override
	public void update(int ndx) throws PAModelException
	{
		_src.update(_ndx[ndx]);
	}

	@Override
	public void applyMismatches(Mismatch pmm, Mismatch qmm, int[] subset) throws PAModelException
	{
		throw new UnsupportedOperationException(
				"Subset of a sublist not implemented (yet)");
	}
}
