package com.powerdata.openpa.tools.psmfmt;

public enum SwitchType implements VersionedDoc
{   
	
	ID, Name, OpenUnderLoad, CloseUnderLoad;

	@Override
	public String getVersion() {return "1.10";}
}
