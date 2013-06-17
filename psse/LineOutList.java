package com.powerdata.openpa.psse;


public abstract class LineOutList extends PsseBaseOutputList<LineOut>
{

	public LineOutList(PsseOutputModel model) {super(model);}

	/** set from-side active power in p.u. */
	public abstract void setFromActvPwr(int ndx, float p);
	/** set to-side active power in p.u. */
	public abstract void setToActvPwr(int ndx, float p);
	/** set from-side reactive power in p.u. */
	public abstract void setFromReacPwr(int ndx, float q);
	/** set to-side reactive power in p.u. */
	public abstract void setToReacPwr(int ndx, float q);
	
	/** set from-side MW */
	public abstract void setFromMW(int ndx, float mw);
	/** set to-side MW */
	public abstract void setToMW(int ndx, float mw);
	/** set from-side MVAr */
	public abstract void setFromMVAr(int ndx, float mvar);
	/** set to-side MVAr */
	public abstract void setToMVAr(int ndx, float mvar);
	
	
}
