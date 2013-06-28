package com.powerdata.openpa.psse;

public abstract class PsseOutputModel extends PsseModel implements BaseOutputGroup
{
	/** get input view that reflects updates to data */
	public abstract PsseInputModel getDynamicInput();
}
