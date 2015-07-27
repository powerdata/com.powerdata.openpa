package com.powerdata.openpa.tools.psmfmt;

public enum Substation implements VersionedDoc
{
	ID, Name, Organization, ControlArea, LoadArea;

	@Override
	public String getVersion() {return "1.10";}
}
