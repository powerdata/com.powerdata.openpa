package com.powerdata.openpa.tools.psmfmt;

public enum CaseLoad implements VersionedDoc
{
	ID, MW, MVAr, InService;

	@Override
	public String getVersion() {return "1.9";}
}
