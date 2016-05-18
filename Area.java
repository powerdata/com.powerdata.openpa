package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class Area extends Group
{
	protected AreaList _list;
	public Area(AreaList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
}
