package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public interface TwoTermDev extends BaseObject
{
	public Bus getFromBus() throws PsseModelException;
	public Bus getToBus() throws PsseModelException;
}
