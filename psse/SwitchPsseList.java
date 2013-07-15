package com.powerdata.openpa.psse;

public abstract class SwitchPsseList extends PsseBaseInputList<SwitchPsse>
{
	public SwitchPsseList(PsseInputModel model) {super(model);}

	/* Standard object retrieval */
	/** Get a SwitchIn by it's index. */
	@Override
	public SwitchPsse get(int ndx) { return new SwitchPsse(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public SwitchPsse get(String id) { return super.get(id); }

	public abstract BusIn getBus1(int ndx) throws PsseModelException;
	public abstract BusIn getBus2(int ndx) throws PsseModelException;
	public abstract String getName(int ndx) throws PsseModelException;
	public abstract SwitchState getState(int ndx) throws PsseModelException;
	public abstract void setState(int ndx, SwitchState state) throws PsseModelException;
}
