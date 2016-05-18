package com.powerdata.openpa.pwrflow;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
