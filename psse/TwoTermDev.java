package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public interface TwoTermDev extends BaseObject
{
	public String getI() throws PsseModelException;
	public String getJ() throws PsseModelException;
	public Bus getFromBus() throws PsseModelException;
	public Bus getToBus() throws PsseModelException;
	/** get in service flag */
	public boolean isInSvc() throws PsseModelException;
}
