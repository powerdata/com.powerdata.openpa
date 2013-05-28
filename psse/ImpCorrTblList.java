package com.powerdata.openpa.psse;
//TODO:  Implement class

public abstract class ImpCorrTblList<T extends ImpCorrTbl> extends PsseBaseList<T>
{
	/* Standard object retrieval */

	/** Get a ImpCorrTbl by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new ImpCorrTbl(ndx,this); }
	/** Get a ImpCorrTbl by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	public ImpCorrTblList(PsseModel model) {super(model);}
}
