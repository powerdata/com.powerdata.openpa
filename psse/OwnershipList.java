package com.powerdata.openpa.psse;

public abstract class OwnershipList<T extends Ownership> extends PsseBaseList<T>
{
	public OwnershipList(PsseModel model) {super(model);}

	/* convenience methods */
	public Owner getOwner(int ndx) throws PsseModelException {return _model.getOwners().get(getO(ndx));}

	/* Standard object retrieval */

	/** Get an Ownership by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new Ownership(ndx,this); }
	/** Get an Ownership by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
