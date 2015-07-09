package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import com.powerdata.openpa.tools.LinkNet;

@Deprecated
public abstract class BMtrxElemBldr extends AbstractList<com.powerdata.openpa.pwrflow.BMtrxElemBldr.BMtrxElem>
{
	public interface BMtrxElem
	{
		float getFromSelfB();
		float getToSelfB();
		float getTransferB();
	}

	protected int _size;
	
	public BMtrxElemBldr(LinkNet adj)
	{
		_size = adj.getBranchCount();
	}

	@Override
	public int size()
	{
		return _size;
	}

}
