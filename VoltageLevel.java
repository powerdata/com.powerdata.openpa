package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class VoltageLevel extends Group 
{
	protected VoltageLevelList _list;
	
	public VoltageLevel(VoltageLevelList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public float getBaseKV() throws PAModelException
	{
		return _list.getBaseKV(_ndx);
	}
	
	public void setBaseKV(float k) throws PAModelException
	{
		_list.setBaseKV(_ndx, k);
	}
	
}
