package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;

public interface PsseModelLog
{
	public void log(LogSev severity, AbstractBaseObject obj, String msg) throws PsseModelException;
}
