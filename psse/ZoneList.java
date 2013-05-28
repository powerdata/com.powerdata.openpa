package com.powerdata.openpa.psse;

public abstract class ZoneList<T extends Zone> extends PsseBaseList<T>
{
	public ZoneList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Zone by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new Zone(ndx,this); }
	/** Get a Zone by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	public abstract int getI(int ndx);
	public abstract String getZONAME(int ndx);
}
