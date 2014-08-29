package com.powerdata.openpa.tools.psmfmt;

public enum Line implements VersionedDoc
{
	ID, Name, Node1, Node2, R, X, Bch, NormalOperatingLimit;

	@Override
	public String getVersion() {return "1.10";}
}
