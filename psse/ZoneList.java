package com.powerdata.openpa.psse;

public abstract class ZoneList extends PsseBaseList<Zone>
{
	public ZoneList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Zone by it's index. */
	@Override
	public Zone get(int ndx) { return new Zone(ndx,this); }
	/** Get a Zone by it's ID. */
	@Override
	public Zone get(String id) { return super.get(id); }

	public abstract int getI(int ndx);
	public abstract String getZONAME(int ndx);
}
