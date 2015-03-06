package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModelException;

public interface MismatchReporter
{
	MismatchReporter reportBegin(BusList buses);
	/**
	 * Reports a mismatch
	 * @param pmm active power mismatch in MW for each bus
	 * @param qmm reactive power mismatch in MVAr for each bus
	 * @param vm Voltage magnitude (per-unit)
	 * @param va Voltage angle (DEG)
	 * @param type
	 * @throws PAModelException
	 */
	void reportMismatch(float[] pmm, float[] qmm, float[] vm, float[] va, BusType[] type)
		throws PAModelException;
	void reportEnd();
	/** Only report the final mismatches */
	boolean reportLast();
}