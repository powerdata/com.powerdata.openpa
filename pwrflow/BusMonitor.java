package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.BasicGroupIndex;
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;


/**
 * Monitor conditions on buses and take action as appropriate
 * 
 * @author chris@powerdata.com
 *
 */

public abstract class BusMonitor
{
	@FunctionalInterface
	protected interface Monitor
	{
		boolean test(float mm, int i) throws PAModelException;
	}
	
	@FunctionalInterface
	public interface Action
	{
		void take(Bus bus, float mismatch) throws PAModelException;
	}
	
	/** get monitor positions (index within _mbus) for each island */
	protected List<int[]> _posbyisland;
	/** Keep a hold of the bus types as a convenience */
	BusTypeUtil _btu;
	/** make a single list of all monitored buses.  Defines primary testing order */
	int[] _mbus;
	/** system buses (topology-dependent, must be consistent with the topology given to btu) */
	BusList _sbus;
	/** Monitor set */
	Monitor[] _monitors;
	/** no monitoring */
	static protected Monitor _Nomon = (i,j) -> false;

	/**
	 * Create a new BusMonitor
	 * @param sbus system buses (topology-dependent, must be consistent with the topology given to btu)
	 * @param btu Utility to extract buses for a given type
	 * @param types Set of types to monitor
	 * @param hot List of hot (energized) islands
	 * @param defmon Default Monitor object to populate set
	 * @throws PAModelException
	 */
	protected BusMonitor(BusList sbus, BusTypeUtil btu, Set<BusType> types,
			ElectricalIslandList hot) throws PAModelException
	{
		_sbus = sbus;
		_btu = btu;
		int nhot = hot.size(), ntr = 0, nbus = sbus.size();
		int[] bmap = new int[nbus], trbus = new int[nbus];
		Arrays.fill(bmap, -1);
		Arrays.fill(trbus, -1);
		
		for(int ii = 0; ii < nhot; ++ii)
		{
			for (BusType t : types)
			{
				for(int x : btu.getBuses(t, hot.get(ii)))
				{
					if (includeBus(t, sbus.get(x)))
					{
						trbus[ntr] = x;
						bmap[ntr] = ii;
						++ntr;
					}
				}
			}
		}
		_posbyisland = new BasicGroupIndex(bmap, nhot).map();
		_mbus = Arrays.copyOf(trbus, ntr);
		_monitors = new Monitor[ntr];
	}
	
	/**
	 * Test a bus for inclusion in the monitor list 
	 * @param bus BusType of bus
	 * @param bus Bus to test
	 * @return true if bus should be included for monitoring
	 * @throws PAModelException 
	 */
	protected boolean includeBus(BusType type, Bus bus) throws PAModelException {return true;}
	
	public void monitor(float[] mm, ConvergenceList rv) throws PAModelException
	{
		int nrv = rv.size();
		for(int irv = 0; irv < nrv; ++irv)
		{
			if (testIsland(rv.get(irv)))
			{
				for(int i : _posbyisland.get(irv))
					_monitors[i].test(mm[_mbus[i]], i);
			}
		}
	}

	/**
	 * Test if an island should be monitored
	 * @param convergenceInfo
	 * @return
	 */
	protected boolean testIsland(ConvergenceInfo ci)
	{
		return true;
	}
}
