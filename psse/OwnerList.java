package com.powerdata.openpa.psse;

public abstract class OwnerList<T extends Owner> extends PsseBaseList<T>
{
	public OwnerList(PsseModel model) {super(model);}

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getOWNAME(int ndx) throws PsseModelException;
	
	/* raw data defaults */
	
	public String getDeftOWNAME(int ndx) {return "";}
}
