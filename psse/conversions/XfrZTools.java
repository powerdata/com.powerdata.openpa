package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.util.TransformerRaw;
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
	public Complex convert2W(Transformer xf) throws PsseModelException;
	public Complex convert2W(PhaseShifter ps) throws PsseModelException;
	public Complex convert2W(TransformerRaw xf) throws PsseModelException;
	public DeltaNetwork convert3W(TransformerRaw xf) throws PsseModelException;
}
