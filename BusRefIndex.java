package com.powerdata.openpa;

import com.powerdata.openpa.impl.BusRefIndexI;

/**
 * Provide bus indexes from equipment lists.
 * 
 * For use with applications that make use of indexes rather than objects
 * 
 * Note that indexes are always based in the list derived from the model, either 
 * getBuses() or getSingleBus(), depending on how it is configured
 * 
 * @author chris@powerdata.com
 *
 */

public interface BusRefIndex
{
	/**
	 * return the bus list used for this object, either PAModel.getBuses(), or
	 * getSingleBus(), depending on startup configuration
	 */
	BusList getBuses();
	
	/**
	 * Get the list of bus indexes for the one-terminal device list
	 * @param t1list list of one-terminal devices 
	 * @return array of bus indexes for each device
	 * @throws PAModelException
	 */
	int[] get1TBus(OneTermDevListIfc<? extends OneTermDev> t1list) throws PAModelException;	
	
	/**
	 * Return object for both bus indexes of a list of two-terminal devices
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	public static class TwoTerm
	{
		int[] _f, _t;
		public TwoTerm(int[] f, int[] t)
		{
			_f = f;
			_t = t;
		}
		public int[] getFromBus() {return _f;}
		public int[] getToBus() {return _t;}
	}

	/**
	 * Get lists of bus indexes for two-terminal device list
	 * @param t2list list of two-terminal devices
	 * @return object containing from- and to- bus indexes for each device
	 * @throws PAModelException
	 */
	TwoTerm get2TBus(TwoTermDevListIfc<? extends TwoTermDev> t1list) throws PAModelException;

	/**
	 * Convert a connectivity-based bus to this topology
	 * @param b the Connectivity-based bus
	 * @return toplology-based bus
	 * @throws PAModelException 
	 */
	default Bus getByBus(Bus b) throws PAModelException
	{
		return getBuses().getByBus(b);
	}
	
	/**
	 * Function that takes a list and index, and return a Bus object
	 * 
	 * @author chris@powerdata.com
	 *
	 * @param <T> A list that can return a bus for some reason
	 */
	@FunctionalInterface
	interface BusFunction
	{
		Bus apply(int index) throws PAModelException;
	}

	/**
	 * Get list of bus indexes for any list that return a bus
	 * @param rlist List of objects with a function returning buses
	 * @param bfc 
	 * @return
	 * @throws PAModelException
	 */
	public int[] mapBusFcn(BaseList<? extends BaseObject> list, BusFunction bfc) throws PAModelException;
	
	public static BusRefIndex CreateFromConnectivityBuses(PALists m) throws PAModelException
	{
		return new BusRefIndexI(m.getBuses(), (l,b) -> b);
	}

	public static BusRefIndex CreateFromSingleBuses(PAModel m) throws PAModelException
	{
		return new BusRefIndexI(m.getSingleBus(), GroupListIfc::translateBusIndexes);
	}


}
