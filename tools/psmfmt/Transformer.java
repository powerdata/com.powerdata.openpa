package com.powerdata.openpa.tools.psmfmt;

public enum Transformer implements VersionedDoc
{
	ID, Name, WindingCount;

	@Override
	public String getVersion() {return "1.10";}
}
