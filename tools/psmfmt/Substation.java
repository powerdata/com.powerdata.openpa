package com.powerdata.openpa.tools.psmfmt;

public enum Substation implements VersionedDoc
{
	ID, Name, Organization, ControlArea;

	@Override
	public String getVersion() {return "1.10";}
}
