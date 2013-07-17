package com.powerdata.openpa.psse;

public abstract class ZoneInList extends PsseBaseInputList<ZoneIn>
{
	public static final ZoneInList Empty = new ZoneInList()
	{
		@Override
		public int getI(int ndx) throws PsseModelException {return 0;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	protected ZoneInList() {super();}
	public ZoneInList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Zone by it's index. */
	@Override
	public ZoneIn get(int ndx) { return new ZoneIn(ndx,this); }
	/** Get a Zone by it's ID. */
	@Override
	public ZoneIn get(String id) { return super.get(id); }

	public abstract int getI(int ndx) throws PsseModelException;
	public String getZONAME(int ndx)throws PsseModelException {return "";}
}
