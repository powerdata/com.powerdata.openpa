package com.powerdata.openpa.psse;

public abstract class OwnerInList extends PsseBaseInputList<OwnerIn>
{
	public OwnerInList(PsseInputModel model) {super(model);}

	/* Standard object retrieval */

	/** Get an Owner by it's index. */
	@Override
	public OwnerIn get(int ndx) { return new OwnerIn(ndx,this); }
	/** Get an Owner by it's ID. */
	@Override
	public OwnerIn get(String id) { return super.get(id); }

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getOWNAME(int ndx) throws PsseModelException;
	
	/* raw data defaults */
	
	public String getDeftOWNAME(int ndx) {return "";}
}
