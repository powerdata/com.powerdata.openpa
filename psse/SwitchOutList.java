package com.powerdata.openpa.psse;


public abstract class SwitchOutList extends PsseBaseOutputList<SwitchOut>
{

	public SwitchOutList(PsseOutputModel model) {super(model);}

	public abstract void setSwitchState(int ndx, SwitchState newstate);
	
}
