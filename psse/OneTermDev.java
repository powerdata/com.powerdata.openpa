package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public interface OneTermDev extends BaseObject
{
	public Bus getBus() throws PsseModelException;
	/** get the load MW */
	public float getRTMW() throws PsseModelException;
	/** get the load MVar */
	public float getRTMVar() throws PsseModelException;
	/** set the load MW */
	public void setRTMW(float mw) throws PsseModelException;
	/** set the load MVar */
	public void setRTMVAr(float mvar) throws PsseModelException;
	/** get active power p.u. on 100MVA base */
	public float getRTP() throws PsseModelException;
	/** set active power p.u. on 100MVA base */
	public void setRTP(float p) throws PsseModelException;
	/** get reactive power p.u. on 100MVA base */
	public float getRTQ() throws PsseModelException;
	/** set reactive power p.u. on 100MVA base */
	public void setRTQ(float q) throws PsseModelException;
	public boolean isInSvc() throws PsseModelException;
}
