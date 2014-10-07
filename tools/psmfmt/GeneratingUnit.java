package com.powerdata.openpa.tools.psmfmt;

public enum GeneratingUnit implements VersionedDoc
{
	ID,Name,MinOperatingMW,MaxOperatingMW,GeneratingUnitType,GenControlMode;

	@Override
	public String getVersion() {return "1.10";}
}
