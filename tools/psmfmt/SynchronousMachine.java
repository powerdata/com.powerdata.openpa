package com.powerdata.openpa.tools.psmfmt;

public enum SynchronousMachine implements VersionedDoc
{
	ID, Name, Node, GeneratingUnit, RegulatedNode;

	@Override
	public String getVersion() {return "1.10";}
}
