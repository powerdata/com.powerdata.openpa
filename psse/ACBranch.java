package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public interface ACBranch extends TwoTermDev
{
	/** resistance p.u. on 100 MVA and bus base kv */
	public float getR() throws PsseModelException;
	/** reactance p.u. on 100MVA and bus base KV */
	public float getX() throws PsseModelException;
	/** Get complex impedance p.u. on 100 MVA and bus base kv */
	public Complex getZ() throws PsseModelException;
	/** Get complex admittance (1/Z) p.u. on 100 MVA and bus base kv */
	public Complex getY() throws PsseModelException;
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float getFromTap() throws PsseModelException;
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float getToTap() throws PsseModelException;
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	public float getGmag() throws PsseModelException;
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	public float getBmag() throws PsseModelException;
	/** get from-side charging susceptance */
	public float getFromBchg() throws PsseModelException;
	/** get to-side charging susceptance */
	public float getToBchg() throws PsseModelException;
	/** get phase shift through branch (in RAD)*/
	public float getPhaseShift() throws PsseModelException;
}
