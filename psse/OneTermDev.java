package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public interface OneTermDev extends BaseObject
{
	public Bus getBus() throws PsseModelException;
	/** get the current MW */
	public float getP() throws PsseModelException;
	/** get the current MVar */
	public float getQ() throws PsseModelException;
	/** set the current MW */
	public void setP(float mw) throws PsseModelException;
	/** set the current MVar */
	public void setQ(float mvar) throws PsseModelException;
	/** get active power p.u. on 100MVA base */
	public float getPpu() throws PsseModelException;
	/** set active power p.u. on 100MVA base */
	public void setPpu(float p) throws PsseModelException;
	/** get reactive power p.u. on 100MVA base */
	public float getQpu() throws PsseModelException;
	/** set reactive power p.u. on 100MVA base */
	public void setQpu(float q) throws PsseModelException;
	public boolean isInSvc() throws PsseModelException;
	public void setInSvc(boolean state) throws PsseModelException;
}
