package com.powerdata.openpa.psse;
//TODO:  Implement class

public abstract class ImpCorrTblInList extends PsseBaseInputList<ImpCorrTblIn>
{
	/* Standard object retrieval */

	/** Get a ImpCorrTbl by it's index. */
	@Override
	public ImpCorrTblIn get(int ndx) { return new ImpCorrTblIn(ndx,this); }
	/** Get a ImpCorrTbl by it's ID. */
	@Override
	public ImpCorrTblIn get(String id) { return super.get(id); }

	public ImpCorrTblInList(PsseInputModel model) {super(model);}
}
