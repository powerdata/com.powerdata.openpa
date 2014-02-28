package com.powerdata.openpa.psse;

public abstract class ZoneList extends PsseBaseList<Zone>
{
	public static final ZoneList Empty = new ZoneList()
	{
		@Override
		public int getI(int ndx) {return 0;}
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
	};
	protected ZoneList() {super();}
	public ZoneList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Zone by it's index. */
	@Override
	public Zone get(int ndx) { return new Zone(ndx,this); }
	/** Get a Zone by it's ID. */
	@Override
	public Zone get(String id) { return super.get(id); }

	public abstract int getI(int ndx) throws PsseModelException;
	public String getZONAME(int ndx)throws PsseModelException {return "";}
}
