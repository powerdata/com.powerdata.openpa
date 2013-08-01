package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

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
	public void setRTMVar(float mvar) throws PsseModelException;
	/** get the complex power p.u. on 100MVA base */
	public Complex getRTS() throws PsseModelException;
	/** set complex power p.u. on 100MVA base */
	public void setRTS(Complex s) throws PsseModelException;
	public boolean isInSvc() throws PsseModelException;
}
