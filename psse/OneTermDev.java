package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public interface OneTermDev extends BaseObject
{
	public Bus getBus() throws PsseModelException;
	/** set RT complex power */
	public void setRTS(Complex s) throws PsseModelException;
	/** get RT complex power */
	public Complex getRTS() throws PsseModelException;

}
