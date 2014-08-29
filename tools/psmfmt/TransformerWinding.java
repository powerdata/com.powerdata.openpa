package com.powerdata.openpa.tools.psmfmt;

public enum TransformerWinding implements VersionedDoc
{
	ID, Name, Transformer, Node1, Node2, R, X, Bmag, NormalOperatingLimit;

	@Override
	public String getVersion() {return "1.10";}
}
