package com.powerdata.openpa.psse;
//TODO:  Implement class

public abstract class ImpCorrTblList extends PsseBaseList<ImpCorrTbl>
{
	public static final ImpCorrTblList Empty = new ImpCorrTblList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};

	protected ImpCorrTblList() {super(null);}
	public ImpCorrTblList(PsseModel model) {super(model);}
	
	
	/* Standard object retrieval */

	/** Get a ImpCorrTbl by it's index. */
	@Override
	public ImpCorrTbl get(int ndx) { return new ImpCorrTbl(ndx,this); }
	/** Get a ImpCorrTbl by it's ID. */
	@Override
	public ImpCorrTbl get(String id) { return super.get(id); }

}
