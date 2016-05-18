package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class FixedShunt extends OneTermDev
{
	FixedShuntListIfc<? extends FixedShunt> _list;
	
	public FixedShunt(FixedShuntListIfc<? extends FixedShunt> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get shunt admittance in MVAr @ unity bus voltage */
	public float getB() throws PAModelException
	{
		return _list.getB(_ndx);
	}
	
	/** set shunt admittance in MVAr @ unity bus voltage */
	public void setB(float b) throws PAModelException
	{
		_list.setB(_ndx, b);
	}
}
