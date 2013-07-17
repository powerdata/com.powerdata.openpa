package com.powerdata.openpa.psse;

public abstract class OwnershipInList extends PsseBaseInputList<OwnershipIn>
{
	protected OwnedEquip _eq;
	
	public static final OwnershipInList Empty = new OwnershipInList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	protected OwnershipInList() {super();}
	public OwnershipInList(PsseModel model, OwnedEquip eq)
	{
		super(model);
		_eq = eq;
	}

	/* Standard object retrieval */

	/** Get an Ownership by it's index. */
	@Override
	public OwnershipIn get(int ndx) { return new OwnershipIn(ndx,this); }
	/** Get an Ownership by it's ID. */
	@Override
	public OwnershipIn get(String id) { return super.get(id); }

	/* convenience methods */
	public OwnerIn getOwner(int ndx) throws PsseModelException {return _model.getOwners().get(getO(ndx));}

	/* raw PSS/e methods */
	public int getO(int ndx) throws PsseModelException {return _eq.getBus().getOWNER();}
	public float getF(int ndx) throws PsseModelException {return 100f;}

	@Override
	public int size() {return 1;}
}
