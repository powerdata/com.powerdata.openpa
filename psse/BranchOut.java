package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class BranchOut extends BaseObject
{
	protected BranchOutList _list;
	
	public BranchOut(int ndx, BranchOutList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/** set from-side active power in p.u. */
	public void setFromActvPwr(float p) {_list.setFromActvPwr(_ndx, p);}
	/** set to-side active power in p.u. */
	public void setToActvPwr(float p) {_list.setToActvPwr(_ndx, p);}
	/** set from-side reactive power in p.u. */
	public void setFromReacPwr(float q) {_list.setFromReacPwr(_ndx, q);}
	/** set to-side reactive power in p.u. */
	public void setToReacPwr(float q) {_list.setToReacPwr(_ndx, q);}
	
	/** set from-side MW */
	public void setFromMW(float mw) {_list.setFromMW(_ndx, mw);}
	/** set to-side MW */
	public void setToMW(float mw) {_list.setToMW(_ndx, mw);}
	/** set from-side MVAr */
	public void setFromMVAr(float mvar) {_list.setFromMVAr(_ndx, mvar);}
	/** set to-side MVAr */
	public void setToMVAr(float mvar) {_list.setToMVAr(_ndx, mvar);}



}
