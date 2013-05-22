package com.powerdata.openpa.psse;

public abstract class OwnershipList<T extends Ownership> extends PsseBaseList<T>
{
	public OwnershipList(PsseModel model) {super(model);}

	/* convenience methods */
	public Owner getOwner(int ndx) throws PsseModelException {return _model.getOwners().get(getO(ndx));}

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
