package com.powerdata.openpa;

import java.util.List;

/**
 * Abstract groupings of buses on some (or no) criteria
 * 
 * @author chris@powerdata.com
 *
 */
public interface BusGrpMap
{
	int size();

	/** get group index for a specific bus index */
	int getGrp(int index);

	List<int[]> map();
	
	
	public static BusGrpMap Empty = new BusGrpMap()
	{
		@Override
		public int size() {return 0;}

		@Override
		public int getGrp(int index) {return -1;}

		@Override
		public List<int[]> map() {return null;}

	};
}