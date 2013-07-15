package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;

/**
 * Normalize 2-winding transformer impedance values to p.u. on:
 * Apparent power base: 100MVA
 * Voltage base: Winding bus
 * nominal tap, if configured.
 * 
 * @author chris@powerdata.com
 *
 */
public interface XfrZTools
{
	public Complex convert2W(TransformerRawList list, int ndx) throws PsseModelException;
	public DeltaNetwork convert3W(TransformerRawList list, int ndx) throws PsseModelException;
}
