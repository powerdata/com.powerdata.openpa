package com.powerdata.openpa.tools.psmfmt;

public enum CaseRatioTapChanger implements VersionedDoc
{
	ID, Ratio, LTCEnable, TapPosition;

	@Override
	public String getVersion() {return "1.9";}
}
