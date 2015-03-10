package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.PAModelException;

/**
 * Provide appropriate data access for 1-terminal device data during
 * mismatch calculations
 */
@FunctionalInterface
interface ActiveDataAccess
{
	float[] get() throws PAModelException;
}