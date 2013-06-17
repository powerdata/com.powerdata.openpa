package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class TransformerOut extends BaseObject
{

	public TransformerOut(int ndx)
	{
		super(ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getObjectID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/** set from-side active power in p.u. */
	public void setFromActvPwr(float p) {/*TODO: */}
	/** set to-side active power in p.u. */
	public void setToActvPwr(float p) {/*TODO: */}
	/** set from-side reactive power in p.u. */
	public void setFromReacPwr(float q) {/*TODO: */}
	/** set to-side reactive power in p.u. */
	public void setToReacPwr(float q) {/*TODO: */}
	
	/** set from-side MW */
	public void setFromMW(float mw) {/*TODO: */}
	/** set to-side MW */
	public void setToMW(float mw) {/*TODO: */}
	/** set from-side MVAr */
	public void setFromMVAr(float mvar) {/*TODO: */}
	/** set to-side MVAr */
	public void setToMVAr(float mvar) {/*TODO: */}

	
}
