package com.powerdata.openpa.tools.psmfmt;

public enum CasePhaseTapChanger implements VersionedDoc
{
	ID, ControlStatus, PhaseShift, TapPosition;

	@Override
	public String getVersion() {return "1.9";}
}
