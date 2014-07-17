package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.AbstractBaseObject;
import com.powerdata.openpa.psse.PsseModelException;

public interface PsseModelLog
{
	public void log(LogSev severity, AbstractBaseObject obj, String msg) throws PsseModelException;
}
