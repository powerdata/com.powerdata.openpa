package com.powerdata.openpa;

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