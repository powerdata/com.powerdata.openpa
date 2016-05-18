package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import java.util.List;

/**
 * Partition buses into groupings
 * 
 * @author chris@powerdata.com
 *
 */
public interface GroupIndex
{
	int size();

	/** get group index for a specific bus index */
	int getGrp(int index);

	List<int[]> map();
	
	
	public static GroupIndex Empty = new GroupIndex()
	{
		@Override
		public int size() {return 0;}

		@Override
		public int getGrp(int index) {return -1;}

		@Override
		public List<int[]> map() {return null;}

	};
}