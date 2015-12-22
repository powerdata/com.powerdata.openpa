package com.powerdata.openpa.tools.psmfmt;

public enum CaseSVC implements VersionedDoc
{
	ID, Mode, MVArSetPoint, VoltageSetpoint, InService;

	@Override
	public String getVersion() {return "1.9";}
}
