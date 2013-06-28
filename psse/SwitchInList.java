package com.powerdata.openpa.psse;

public abstract class SwitchInList extends PsseBaseInputList<SwitchIn>
{
	public SwitchInList(PsseInputModel model) {super(model);}

	/* Standard object retrieval */
	/** Get a SwitchIn by it's index. */
	@Override
	public SwitchIn get(int ndx) { return new SwitchIn(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public SwitchIn get(String id) { return super.get(id); }

	public abstract BusIn getBus(int ndx);
	public abstract String getName(int ndx);
	public abstract SwitchState getState(int ndx);

}
