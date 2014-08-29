package com.powerdata.openpa.tools.psmfmt;

public enum Node implements VersionedDoc
{
	ID, Name, NominalKV, Substation, IsBusBarSection, FrequencySourcePriority;

	@Override
	public String getVersion() {return "1.10";}
}
