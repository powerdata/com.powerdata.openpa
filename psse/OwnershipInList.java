package com.powerdata.openpa.psse;

public abstract class OwnershipInList extends PsseBaseInputList<OwnershipIn>
{
	public OwnershipInList(PsseInputModel model) {super(model);}

	/* convenience methods */
	public OwnerIn getOwner(int ndx) throws PsseModelException {return _model.getOwners().get(getO(ndx));}

	/* Standard object retrieval */

	/** Get an Ownership by it's index. */
	@Override
	public OwnershipIn get(int ndx) { return new OwnershipIn(ndx,this); }
	/** Get an Ownership by it's ID. */
	@Override
	public OwnershipIn get(String id) { return super.get(id); }

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
