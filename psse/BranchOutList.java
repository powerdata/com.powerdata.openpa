package com.powerdata.openpa.psse;

public abstract class BranchOutList extends PsseBaseOutputList<BranchOut>
{

	public BranchOutList(PsseOutputModel model) { super(model);}

	public abstract void setFromActvPwr(int _ndx, float p);
	public abstract void setToActvPwr(int _ndx, float p);
	public abstract void setFromReacPwr(int _ndx, float q);
	public abstract void setToReacPwr(int _ndx, float q);
	public abstract void setFromMW(int _ndx, float mw);
	public abstract void setToMW(int _ndx, float mw);
	public abstract void setFromMVAr(int _ndx, float mvar);
	public abstract void setToMVAr(int _ndx, float mvar);
}
