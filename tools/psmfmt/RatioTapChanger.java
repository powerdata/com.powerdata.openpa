package com.powerdata.openpa.tools.psmfmt;

public enum RatioTapChanger implements VersionedDoc
{
	ID, Name, TransformerWinding, TapNode, MinKV, MaxKV, MinTap, MaxTap, NeutralTap, StepSize;
	
	@Override
	public String getVersion() {return "1.10";}
}
