package com.powerdata.openpa.psse;

public abstract class OwnerList<T extends Owner> extends PsseBaseList<T>
{
	public OwnerList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get an Owner by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new Owner(ndx,this); }
	/** Get an Owner by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getOWNAME(int ndx) throws PsseModelException;
	
	/* raw data defaults */
	
	public String getDeftOWNAME(int ndx) {return "";}
}
