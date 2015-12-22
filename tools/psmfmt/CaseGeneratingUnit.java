package com.powerdata.openpa.tools.psmfmt;

public enum CaseGeneratingUnit implements VersionedDoc
{
	ID, MW, MWSetPoint, GeneratorOperatingMode, InService;

	@Override
	public String getVersion() {return "1.9";}
}
