package com.powerdata.openpa.tools.psmfmt;

public enum CaseRatioTapChanger implements VersionedDoc
{
	ID, TapPosition, LTCEnable, Ratio;

	@Override
	public String getVersion() {return "1.8";}
}
