package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public interface ACBranch extends TwoTermDev
{
	/** Get complex impedance p.u. on 100 MVA and bus base kv */
	public Complex getZ() throws PsseModelException;
	/** Get complex admittance (1/Z) p.u. on 100 MVA and bus base kv */
	public Complex getY() throws PsseModelException;
	/** get from-side Admittance (charging or magnetizing) p.u. on 100MVA base and bus base KV*/
	public Complex getFromYcm() throws PsseModelException;
	/** get to-side Admittance (charging or magnetizing) p.u. on 100MVA base and bus base KV*/
	public Complex getToYcm() throws PsseModelException;
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float getFromTap() throws PsseModelException;
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float getToTap() throws PsseModelException;
	/** get phase shift through branch (in RAD)*/
	public float getPhaseShift() throws PsseModelException;
}
