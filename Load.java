package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class Load extends OneTermDev 
{
	LoadList _list;
	
	public Load(LoadList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** Get Max Load MW */
	public float getMaxP() throws PAModelException
	{
		return _list.getMaxP(_ndx);
	}
	
	/** Set Max Load MW */
	public void setMaxP(float mw) throws PAModelException
	{
		_list.setMaxP(_ndx, mw);
	}

	/** Get Max Load MVAr */
	public float getMaxQ() throws PAModelException
	{
		return _list.getMaxQ(_ndx);
	}

	/** Set Max Load MVAr */
	public void setMaxQ(float mvar) throws PAModelException
	{
		_list.setMaxQ(_ndx, mvar);
	}
}
