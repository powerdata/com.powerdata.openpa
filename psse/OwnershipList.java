package com.powerdata.openpa.psse;

public abstract class OwnershipList<T extends Ownership> extends PsseBaseList<T>
{
	public OwnershipList(PsseModel model) {super(model);}

	/* convenience methods */
	public abstract Owner getOwner(int ndx);

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
