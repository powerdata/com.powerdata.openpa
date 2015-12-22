package com.powerdata.openpa.tools.psmfmt;

public enum CaseSynchronousMachine implements VersionedDoc
{
	ID, SynchronousMachineOperatingMode, AVRMode, KVSetPoint, MVArSetpoint, MVAr, InService;

	@Override
	public String getVersion() {return "1.9";}
}
