package com.powerdata.openpa.psse;
//TODO:  Implement class

public abstract class ImpCorrTblList extends PsseBaseInputList<ImpCorrTbl>
{
	/* Standard object retrieval */

	/** Get a ImpCorrTbl by it's index. */
	@Override
	public ImpCorrTbl get(int ndx) { return new ImpCorrTbl(ndx,this); }
	/** Get a ImpCorrTbl by it's ID. */
	@Override
	public ImpCorrTbl get(String id) { return super.get(id); }

	public ImpCorrTblList(PsseModel model) {super(model);}
}
