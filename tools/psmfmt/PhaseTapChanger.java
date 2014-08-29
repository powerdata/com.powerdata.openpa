package com.powerdata.openpa.tools.psmfmt;

public enum PhaseTapChanger implements VersionedDoc
{
	ID, Name, TapNode, TransformerWinding;

	@Override
	public String getVersion() {return "1.10";}
}
