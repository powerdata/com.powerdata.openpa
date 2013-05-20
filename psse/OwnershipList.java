package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class OwnershipList<T extends Ownership> extends BaseList<T>
{
	/* convenience methods */
	public abstract Owner getOwner(int ndx);

	/* raw PSS/e methods */
	public abstract int getO(int ndx);
	public abstract float getF(int ndx);
}
