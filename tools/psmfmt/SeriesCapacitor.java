package com.powerdata.openpa.tools.psmfmt;

public enum SeriesCapacitor implements VersionedDoc
{
	ID, Name, Node1, Node2, R, X, NormalOperatingLimit;

	@Override
	public String getVersion() {return "1.10";}
}
