package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseObject;

/**
 * Provide generalized access to all branches.  
 * @author chris@powerdata.com
 * 
 * TODO:  Determine how to best add DC links in this if appropriate.
 *
 */
public class Branch extends BaseObject
{
	private BranchList<?> _list;
	
	public Branch(int ndx, BranchList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getObjectID() {return _list.getObjectID(getIndex());}
	/** get from-side node */
	public int getFromNode() {return _list.getFromNode(getIndex());}
	/** get to-side node */
	public int getToNode() {return _list.getToNode(getIndex());}
	/** get resistance PU on 100 MVA base */
	public float getR() {return _list.getR(getIndex());}
	/** get reactance PU on 100 MVA base */
	public float getX() {return _list.getX(getIndex());}
	/** get from-side charging susceptance.  Defaults to 0 if not line */
	public float getFromBChg() {return _list.getFromBChg(getIndex());}
	/** get to-side charging susceptance.  Defaults to 0 if not line */
	public float getToBChg() {return _list.getToBChg(getIndex());}
	/** get from-side tap ratio.  Defaults to 1 if not relevant (i.e. not a transformer) */
	public float getFromTapRatio() {return _list.getFromTapRatio(getIndex());}
	/** get to-side tap ratio.  Defaults to 1 if not relevant (i.e. not a transformer) */
	public float getToTapRatio() {return _list.getToTapRatio(getIndex());}
	/** get phase shift.  Defaults to 0 if not relevant (not a phase shifter or fixed shift on transformer) */
	public float getPhaseShift() {return _list.getPhaseShift(getIndex());}

	
	public void updateActvPower(float p) {_list.updateActvPower(getIndex(), p);}
	public void updateReacPower(float q) {_list.updateReacPower(getIndex(), q);}

	/* possible methods using different units */
//	public void updateMW(float p) {_list.updateMW(getIndex(), p);}
//	public void updateMVAr(float q) {_list.updateMVAr(getIndex(), q);}
}
