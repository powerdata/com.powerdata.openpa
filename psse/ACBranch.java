package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public interface ACBranch extends TwoTermDev
{
	/** Get complex impedance p.u. on 100 MVA and bus base kv */
	public Complex getZ() throws PsseModelException;
}
