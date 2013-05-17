package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class BranchList extends AbstractList<Branch> implements BaseList
{
	/** get from-side node */
	public abstract int getFromNode(int ndx);
	/** get to-side node */
	public abstract int getToNode(int ndx);
	/** get resistance PU on 100 MVA base */
	public abstract float getR(int ndx);
	/** get reactance PU on 100 MVA base */
	public abstract float getX(int ndx);
	/** get from-side charging susceptance.  Defaults to 0 if not line */
	public abstract float getFromBChg(int ndx);
	/** get to-side charging susceptance.  Defaults to 0 if not line */
	public abstract float getToBChg(int ndx);
	/** get from-side tap ratio.  Defaults to 1 if not relevant (i.e. not a transformer) */
	public abstract float getFromTapRatio(int ndx);
	/** get to-side tap ratio.  Defaults to 1 if not relevant (i.e. not a transformer) */
	public abstract float getToTapRatio(int ndx);
	/** get phase shift.  Defaults to 0 if not relevant (not a phase shifter or fixed shift on transformer) */
	public abstract float getPhaseShift(int ndx);

	
	
	public abstract void updateActvPower(int ndx, float p);
	public abstract void updateReacPower(int ndx, float q);
	
//	public abstract void updateMW(int ndx, float p);
//	public abstract void updateMVAr(int ndx, float q);
	
	@Override
	public Branch get(int ndx) {return new Branch(ndx, this);}
	
	public abstract StringAttrib<Branch> mapStringAttribute(String name);

}
