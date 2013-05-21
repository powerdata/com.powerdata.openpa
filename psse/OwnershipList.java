package com.powerdata.openpa.psse;

import java.io.IOException;

public abstract class OwnershipList<T extends Ownership> extends PsseBaseList<T>
{
	public OwnershipList(PsseModel model) {super(model);}

	/* convenience methods */
	public Owner getOwner(int ndx) throws IOException {return _model.getOwners().get(getO(ndx));}

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
