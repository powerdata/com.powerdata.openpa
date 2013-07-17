package com.powerdata.openpa.psse;

public abstract class OwnerInList extends PsseBaseInputList<OwnerIn>
{
	public static final OwnerInList Empty = new OwnerInList()
	{
		@Override
		public int getI(int ndx) throws PsseModelException {return 0;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	protected OwnerInList() {super();}
	public OwnerInList(PsseModel model) {super(model);}

	/** Get an Owner by it's index. */
	@Override
	public OwnerIn get(int ndx) { return new OwnerIn(ndx,this); }
	/** Get an Owner by it's ID. */
	@Override
	public OwnerIn get(String id) { return super.get(id); }

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public String getOWNAME(int ndx) {return "";}
	
}
