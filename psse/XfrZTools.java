package com.powerdata.openpa.psse;

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
	public Complex convert2W(TransformerIn xfr);
	public DeltaNetwork convert3W(TransformerIn xfr);
}
