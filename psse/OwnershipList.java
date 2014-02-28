package com.powerdata.openpa.psse;

public abstract class OwnershipList extends PsseBaseList<Ownership>
{
	protected OwnedEquip _eq;
	
	public static final OwnershipList Empty = new OwnershipList()
	{
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
	};
	protected OwnershipList() {super();}
	public OwnershipList(PsseModel model, OwnedEquip eq)
	{
		super(model);
		_eq = eq;
	}

	/* Standard object retrieval */

	/** Get an Ownership by it's index. */
	@Override
	public Ownership get(int ndx) { return new Ownership(ndx,this); }
	/** Get an Ownership by it's ID. */
	@Override
	public Ownership get(String id) { return super.get(id); }

	/* convenience methods */
	public Owner getOwner(int ndx) throws PsseModelException {return _model.getOwners().get(getO(ndx));}

	/* raw PSS/e methods */
	public int getO(int ndx) throws PsseModelException {return _eq.getBus().getOWNER();}
	public float getF(int ndx) throws PsseModelException {return 100f;}

	@Override
	public int size() {return 1;}
}
