package com.powerdata.openpa.psse;

public abstract class OwnerList extends PsseBaseList<Owner>
{
	public OwnerList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get an Owner by it's index. */
	@Override
	public Owner get(int ndx) { return new Owner(ndx,this); }
	/** Get an Owner by it's ID. */
	@Override
	public Owner get(String id) { return super.get(id); }

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getOWNAME(int ndx) throws PsseModelException;
	
	/* raw data defaults */
	
	public String getDeftOWNAME(int ndx) {return "";}
}
