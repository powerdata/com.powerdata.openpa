package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.AbstractBaseObject;

public interface PsseModelLog
{
	public void log(LogSev severity, AbstractBaseObject obj, String msg) throws PsseModelException;
}
