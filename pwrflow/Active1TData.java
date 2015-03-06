package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.PAMath;

/**
 * Provide housekeeping for a more general view for accessing MW / MVAr
 * injections on 1-terminal devices
 * 
 * @author chris@powerdata.com
 *
 */
public class Active1TData
{
	int[] _bus;
	ActiveDataAccess _qa, _pa;
	float _sbase;
	/** 
	 * Create a new active data object
	 * @param bri Bus reference object in use (either single-bus or connectivity bus)
	 * @param actvdata list of one-terminal considered "active" 
	 * @param pa list accessor for MW
	 * @param qa list accessor for MVAr
	 * @throws PAModelException
	 */
	public Active1TData(BusRefIndex bri,
			OneTermDevListIfc<? extends OneTermDev> actvdata,
			ActiveDataAccess pa, ActiveDataAccess qa, float sbase) throws PAModelException
	{
		_bus = bri.get1TBus(actvdata);
		_pa = pa;
		_qa = qa;
		_sbase = sbase;
	}
	
	/**0
	 * Get the list access array for MW
	 * @return list access array for MW
	 * @throws PAModelException
	 */
	public float[] getP() throws PAModelException
	{
		return _pa.get();
	}
	/**
	 * Get the list access array for MVAr
	 * @return list access array for MVAr
	 * @throws PAModelException
	 */
	public float[] getQ() throws PAModelException
	{
		return _qa.get();
	}
	/**
	 * Get the device bus indexes
	 * @return device bus indexes
	 */
	public int[] getBus()
	{
		return _bus;
	}
	
	public void applyMismatch(Mismatch pmm, Mismatch qmm) throws PAModelException
	{
		float[] p = pmm.get(), q = qmm.get();
		int n = _bus.length;
		float[] lp = getP(), lq = getQ();
		for(int i=0; i < n; ++i)
		{
			int b = _bus[i];
			p[b] += PAMath.mva2pu(lp[i], _sbase);
			q[b] += PAMath.mva2pu(lq[i], _sbase);
		}
	}
}