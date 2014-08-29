package com.powerdata.openpa.tools.psmfmt;

public enum SVC implements VersionedDoc
{
	ID, Name, Node, MinMVAr, MaxMVAr, Slope;

	@Override
	public String getVersion() {return "1.10";}
}
