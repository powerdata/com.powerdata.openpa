package com.powerdata.openpa.tools.psmfmt;

public enum ReactiveCapabilityCurve implements VersionedDoc
{
	ID, SynchronousMachine, MW, MinMVAr, MaxMVAr;
	public String getVersion() {return "1.10";}
}
