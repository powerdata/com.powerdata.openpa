package com.powerdata.openpa.tools.psmfmt;

public enum CaseLine implements VersionedDoc
{
	ID, FromMW, FromMVAr, ToMW, ToMVAr;

	@Override
	public String getVersion() {return "1.9";}
}
