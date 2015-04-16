package com.powerdata.openpa.tools.psmfmt;

public enum PrimeMover implements VersionedDoc
{
	ID, Name, SynchronousMachine, Type;
	
	@Override
	public String getVersion() {return "1.10";}
}
