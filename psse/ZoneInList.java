package com.powerdata.openpa.psse;

public abstract class ZoneInList extends PsseBaseInputList<ZoneIn>
{
	public ZoneInList(PsseInputModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Zone by it's index. */
	@Override
	public ZoneIn get(int ndx) { return new ZoneIn(ndx,this); }
	/** Get a Zone by it's ID. */
	@Override
	public ZoneIn get(String id) { return super.get(id); }

	public abstract int getI(int ndx);
	public abstract String getZONAME(int ndx);
}
