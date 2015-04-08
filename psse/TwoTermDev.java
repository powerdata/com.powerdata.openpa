package com.powerdata.openpa.psse;


public interface TwoTermDev extends BaseObject
{
	public String getI() throws PsseModelException;
	public String getJ() throws PsseModelException;
	public Bus getFromBus() throws PsseModelException;
	public Bus getToBus() throws PsseModelException;
	/** get in service flag */
	public boolean isInSvc() throws PsseModelException;
	public void setInSvc(boolean state) throws PsseModelException;
}
