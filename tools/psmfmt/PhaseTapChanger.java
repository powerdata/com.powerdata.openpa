package com.powerdata.openpa.tools.psmfmt;

public enum PhaseTapChanger implements VersionedDoc
{
	ID, Name, TransformerWinding, TapNode, MinTap, MaxTap, NeutralTap, StepSize;

	@Override
	public String getVersion() {return "1.10";}
}
