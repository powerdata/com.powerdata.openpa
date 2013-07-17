package com.powerdata.openpa.psse;
//TODO:  Implement class

public abstract class ImpCorrTblInList extends PsseBaseInputList<ImpCorrTblIn>
{
	public static final ImpCorrTblInList Empty = new ImpCorrTblInList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};

	protected ImpCorrTblInList() {super(null);}
	public ImpCorrTblInList(PsseModel model) {super(model);}
	
	
	/* Standard object retrieval */

	/** Get a ImpCorrTbl by it's index. */
	@Override
	public ImpCorrTblIn get(int ndx) { return new ImpCorrTblIn(ndx,this); }
	/** Get a ImpCorrTbl by it's ID. */
	@Override
	public ImpCorrTblIn get(String id) { return super.get(id); }

}
