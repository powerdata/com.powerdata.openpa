package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public interface PsseModelLog
{
	public void log(LogSev severity, BaseObject obj, String msg) throws PsseModelException;
}
