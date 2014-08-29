package com.powerdata.openpa.tools.psmfmt;

public enum Load implements VersionedDoc
{
	ID, Name, Node, LoadArea;

	@Override
	public String getVersion() {return "1.10";}
}
