package com.powerdata.openpa.psse;

public abstract class OwnerList<T extends Owner> extends PsseBaseList<T>
{
	public OwnerList(PsseModel model) {super(model);}

	public abstract int getI(int ndx);
	public abstract String getOWNAME(int ndx);
}
